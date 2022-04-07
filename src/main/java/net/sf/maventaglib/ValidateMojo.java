/**
 *
 * Copyright (C) 2004-2014 Fabrizio Giustina
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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;

import net.sf.maventaglib.checker.Tld;
import net.sf.maventaglib.checker.TldParser;
import net.sf.maventaglib.util.XmlHelper;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.doxia.siterenderer.Renderer;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReport;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.plexus.util.FileUtils;
import org.w3c.dom.Document;

/**
 * Generates a tag library validation report.
 * @goal validate
 * @author Fabrizio Giustina
 * @version $Id: ValidateMojo.java 217 2014-08-15 20:50:32Z fgiust $
 */
public class ValidateMojo
    extends AbstractMavenReport
    implements MavenReport
{

    /**
     * Directory containing tld files. Subdirectories are also processed.
     * @parameter alias="taglib.src.dir" expression="src/main/resources/META-INF"
     */
    private File srcDir;

    /**
     * Output directory for generated docs.
     * @parameter expression="${project.reporting.outputDirectory}"
     */
    private File outputDirectory;

    /**
     * Site renderer component.
     * @component
     * @required
     * @readonly
     */
    private Renderer siteRenderer;

    /**
     * Maven project
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * The directory containing generated test classes of the project being tested.
     * @parameter expression="${project.build.outputDirectory}"
     * @required
     */
    private File buildOutputDirectory;

    /**
     * @see org.apache.maven.reporting.AbstractMavenReport#getOutputDirectory()
     */
    @Override
    protected String getOutputDirectory()
    {
        return outputDirectory.getAbsolutePath();
    }

    /**
     * @see org.apache.maven.reporting.AbstractMavenReport#getProject()
     */
    @Override
    protected MavenProject getProject()
    {
        return project;
    }

    /**
     * @see org.apache.maven.reporting.AbstractMavenReport#getSiteRenderer()
     */
    @Override
    protected Renderer getSiteRenderer()
    {
        return siteRenderer;
    }

    /**
     * @see org.apache.maven.reporting.MavenReport#getName(java.util.Locale)
     */
    public String getName( Locale locale )
    {
        return Messages.getString( "Validate.name" ); //$NON-NLS-1$
    }

    /**
     * @see org.apache.maven.reporting.MavenReport#getDescription(java.util.Locale)
     */
    public String getDescription( Locale locale )
    {
        return Messages.getString( "Validate.description" ); //$NON-NLS-1$
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
                .format( Messages.getString( "Taglib.notadir" ), new Object[] { srcDir //$NON-NLS-1$
                    .getAbsolutePath() } ) );
        }

        getLog()
            .debug(
                    MessageFormat
                        .format( Messages.getString( "Taglib.validating" ), new Object[] { srcDir.getAbsolutePath() } ) ); //$NON-NLS-1$

        DocumentBuilder builder;

        try
        {
            builder = XmlHelper.getDocumentBuilder();
        }
        catch ( MojoExecutionException e )
        {
            throw new MavenReportException( e.getMessage(), e );
        }

        List tlds;
        try
        {
            tlds = FileUtils.getFiles( srcDir, "**/*.tld", null ); //$NON-NLS-1$
        }
        catch ( IOException e )
        {
            throw new MavenReportException( e.getMessage(), e );
        }

        List tldList = new ArrayList();
        for ( Iterator i = tlds.iterator(); i.hasNext(); )
        {
            File current = (File) i.next();

            Document tldDoc;
            try
            {
                tldDoc = builder.parse( current );
            }
            catch ( Exception e )
            {
                throw new MavenReportException( MessageFormat.format( Messages.getString( "Taglib.errorwhileparsing" ), //$NON-NLS-1$
                                                                      new Object[] { current.getAbsolutePath() } ), e );
            }

            Tld tld = TldParser.parse( tldDoc, current.getName() );
            tldList.add( tld );

        }
        if ( tldList.size() == 0 )
        {
            getLog()
                .info(
                       MessageFormat
                           .format(
                                    Messages.getString( "Taglib.notldfound" ), new Object[] { srcDir.getAbsolutePath() } ) ); //$NON-NLS-1$
            return;
        }

        List classPathStrings;
        try
        {
            classPathStrings = this.project.getCompileClasspathElements();
        }
        catch ( DependencyResolutionRequiredException e )
        {
            throw new MavenReportException( e.getMessage(), e );
        }

        List URLs = new ArrayList( classPathStrings.size() );

        Iterator iter = classPathStrings.iterator();
        while ( iter.hasNext() )
        {
            try
            {
                URLs.add( new File( ( (String) iter.next() ) ).toURL() );
            }
            catch ( MalformedURLException e )
            {
                throw new MavenReportException( e.getMessage(), e );
            }
        }

        URLClassLoader projectClassLoader = new URLClassLoader( (URL[]) URLs.toArray( new URL[URLs.size()] ), null );

        ValidateRenderer r = new ValidateRenderer( getSink(), locale,
                                                   (Tld[]) tldList.toArray( new Tld[tldList.size()] ), getLog(),
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
            return FileUtils.getFiles( srcDir, "**/*.tld", null ).size() > 0; //$NON-NLS-1$
        }
        catch ( IOException e )
        {
            getLog().error( e.getMessage(), e );
        }
        return false;
    }

}