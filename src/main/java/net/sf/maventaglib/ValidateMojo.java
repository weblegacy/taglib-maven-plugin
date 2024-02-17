/*
 * The MIT License
 * Copyright © 2004-2014 Fabrizio Giustina
 * Copyright © 2022-2024 Web-Legacy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.sf.maventaglib;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;

import net.sf.maventaglib.checker.Tld;
import net.sf.maventaglib.checker.TldParser;
import net.sf.maventaglib.util.XmlHelper;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.plexus.util.FileUtils;
import org.w3c.dom.Document;

/**
 * Generates a tag library validation report.
 * @author Fabrizio Giustina
 * @version $Id: ValidateMojo.java 217 2014-08-15 20:50:32Z fgiust $
 */
@Mojo(name="validate")
public class ValidateMojo
    extends AbstractMavenReport
{

    /**
     * Directory containing tld files. Subdirectories are also processed.
     */
    @Parameter(alias="taglib.src.dir", defaultValue="src/main/resources/META-INF")
    private File srcDir;

    /*
     * The directory containing generated test classes of the project being tested.
     */
    //@Parameter(property="project.build.outputDirectory", required=true)
    //private File buildOutputDirectory;

    /**
     * @see org.apache.maven.reporting.MavenReport#getName(java.util.Locale)
     */
    public String getName( Locale locale )
    {
        return Messages.getString( locale, "Validate.name" ); //$NON-NLS-1$
    }

    /**
     * @see org.apache.maven.reporting.MavenReport#getDescription(java.util.Locale)
     */
    public String getDescription( Locale locale )
    {
        return Messages.getString( locale, "Validate.description" ); //$NON-NLS-1$
    }

    /**
     * @see org.apache.maven.reporting.MavenReport#getOutputName()
     */
    public String getOutputName()
    {
        return "taglibvalidation"; //$NON-NLS-1$
    }

    /**
     * @see org.apache.maven.reporting.AbstractMavenReport#executeReport(java.util.Locale)
     */
    @Override
    protected void executeReport( Locale locale )
        throws MavenReportException
    {

        if ( !srcDir.isDirectory() )
        {
            throw new MavenReportException( MessageFormat
                .format( Messages.getString( "Taglib.notadir" ), //$NON-NLS-1$
                    srcDir.getAbsolutePath() ) );
        }

        getLog()
            .debug(
                    MessageFormat
                        .format( Messages.getString( "Taglib.validating" ), srcDir.getAbsolutePath() ) ); //$NON-NLS-1$

        DocumentBuilder builder;

        try
        {
            builder = XmlHelper.getDocumentBuilder();
        }
        catch ( MojoExecutionException e )
        {
            throw new MavenReportException( e.getMessage(), e );
        }

        List<File> tlds;
        try
        {
            tlds = FileUtils.getFiles( srcDir, "**/*.tld", null ); //$NON-NLS-1$
        }
        catch ( IOException e )
        {
            throw new MavenReportException( e.getMessage(), e );
        }

        List<Tld> tldList = new ArrayList<>();
        for ( File current : tlds )
        {
            Document tldDoc;
            try
            {
                tldDoc = builder.parse( current );
            }
            catch ( Exception e )
            {
                throw new MavenReportException( MessageFormat.format( Messages.getString( "Taglib.errorwhileparsing" ), //$NON-NLS-1$
                                                                      current.getAbsolutePath() ), e );
            }

            Tld tld = TldParser.parse( tldDoc, current.getName() );
            tldList.add( tld );

        }
        if ( tldList.isEmpty() )
        {
            getLog()
                .info(
                       MessageFormat
                           .format(
                                    Messages.getString( "Taglib.notldfound" ), srcDir.getAbsolutePath() ) ); //$NON-NLS-1$
            return;
        }

        List<String> classPathStrings;
        try
        {
            classPathStrings = this.project.getCompileClasspathElements();
        }
        catch ( DependencyResolutionRequiredException e )
        {
            throw new MavenReportException( e.getMessage(), e );
        }

        List<URL> urls = new ArrayList<>( classPathStrings.size() );

        for ( String classPathString : classPathStrings )
        {
            try
            {
                urls.add( new File( classPathString ).toURI().toURL() );
            }
            catch ( MalformedURLException e )
            {
                throw new MavenReportException( e.getMessage(), e );
            }
        }

        URLClassLoader projectClassLoader = AccessController.doPrivileged(
                (PrivilegedAction<URLClassLoader>) () ->
                        new URLClassLoader( urls.toArray( new URL[0] ), null )
        );

        ValidateRenderer r = new ValidateRenderer( getSink(), locale,
                                                   tldList.toArray( new Tld[tldList.size()] ), getLog(),
                                                   projectClassLoader );

        r.render();

    }

    /**
     * @see org.apache.maven.reporting.AbstractMavenReport#canGenerateReport()
     */
    @Override
    public boolean canGenerateReport()
    {
        if ( !srcDir.isDirectory() )
        {
            return false;
        }

        try
        {
            return !FileUtils.getFiles( srcDir, "**/*.tld", null ).isEmpty(); //$NON-NLS-1$
        }
        catch ( IOException e )
        {
            getLog().error( e.getMessage(), e );
        }
        return false;
    }

}