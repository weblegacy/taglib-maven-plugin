/**
 *
 *  Copyright 2004-2010 Fabrizio Giustina.
 *
 *  Licensed under the Artistic License; you may not use this file
 *  except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://maven-taglib.sourceforge.net/license.html
 *
 *  THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 *  WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */

package net.sf.maventaglib;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;

import net.sf.maventaglib.checker.Tld;
import net.sf.maventaglib.checker.TldParser;
import net.sf.maventaglib.util.XmlHelper;

import org.apache.maven.doxia.siterenderer.Renderer;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenMultiPageReport;
import org.apache.maven.reporting.MavenReport;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.plexus.util.FileUtils;
import org.w3c.dom.Document;

/**
 * Generates a tag reference xdoc that can be integrated in a maven generated site.
 * @goal tagreference
 * @author Fabrizio Giustina
 * @version $Id: TagreferenceMojo.java 206 2010-01-31 09:37:21Z fgiust $
 */
public class TagreferenceMojo
    extends AbstractMavenMultiPageReport
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
     * Whether to parse html in the description of tld info, tags and attributes. The
     * default value is false.
     *
     * @parameter default-value="false"
     */
    private boolean parseHtml;

    /**
     * Maven project
     * @parameter expression="${project}"
     * @readonly
     */
    private MavenProject project;

    /**
     * Site renderer component.
     * @component
     */
    private Renderer siteRenderer;

    /**
     * @see org.apache.maven.reporting.AbstractMavenReport#getOutputDirectory()
     */
    @Override
    protected String getOutputDirectory()
    {
        return this.outputDirectory.getAbsolutePath();
    }

    /**
     * @see org.apache.maven.reporting.AbstractMavenReport#getProject()
     */
    @Override
    protected MavenProject getProject()
    {
        return this.project;
    }

    /**
     * @see org.apache.maven.reporting.AbstractMavenReport#getSiteRenderer()
     */
    @Override
    protected Renderer getSiteRenderer()
    {
        return this.siteRenderer;
    }

    /**
     * @see org.apache.maven.reporting.MavenReport#getOutputName()
     */
    public String getOutputName()
    {
        return "tagreference"; //$NON-NLS-1$
    }

    /**
     * @see org.apache.maven.reporting.MavenReport#getName(java.util.Locale)
     */
    public String getName( Locale locale )
    {
        return Messages.getString( "Tagreference.name" ); //$NON-NLS-1$
    }

    /**
     * @see org.apache.maven.reporting.MavenReport#getDescription(java.util.Locale)
     */
    public String getDescription( Locale locale )
    {
        return Messages.getString( "Tagreference.description" ); //$NON-NLS-1$
    }

    /**
     * Setter for <code>srcDir</code>.
     * @param srcDir The srcDir to set.
     */
    public void setSrcDir( File srcDir )
    {
        this.srcDir = srcDir;
    }

    /**
     * Setter for <code>outputDirectory</code>.
     * @param outputDirectory The outputDirectory to set.
     */
    public void setOutputDirectory( File outputDirectory )
    {
        this.outputDirectory = outputDirectory;
    }

    /**
     * Setter for <code>project</code>.
     * @param project The project to set.
     */
    public void setProject( MavenProject project )
    {
        this.project = project;
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
            tlds = FileUtils.getFiles( srcDir, "**/*.tld", null );
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

        closeReport();

        if ( tldList.size() == 0 )
        {
            getLog()
                .info(
                       MessageFormat
                           .format(
                                    Messages.getString( "Taglib.notldfound" ), new Object[] { srcDir.getAbsolutePath() } ) ); //$NON-NLS-1$
            return;
        }

        new TagreferenceRenderer( getSink(), locale, (Tld[]) tldList.toArray( new Tld[tldList.size()] ), parseHtml, getLog() )
            .render();

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
            return FileUtils.getFiles( srcDir, "**/*.tld", null ).size() > 0;
        }
        catch ( IOException e )
        {
            getLog().error( e.getMessage(), e );
        }
        return false;

    }

    /**
     * @see org.apache.maven.reporting.AbstractMavenMultiPageReport#usePageLinkBar()
     */
    @Override
    public boolean usePageLinkBar()
    {
        return true;
    }

}