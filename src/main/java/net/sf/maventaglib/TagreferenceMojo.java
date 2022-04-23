/*
 * The MIT License
 * Copyright © 2004-2014 Fabrizio Giustina
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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;

import net.sf.maventaglib.checker.Tld;
import net.sf.maventaglib.checker.TldParser;
import net.sf.maventaglib.util.XmlHelper;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.plexus.util.FileUtils;
import org.w3c.dom.Document;


/**
 * Generates a tag reference xdoc that can be integrated in a maven generated site.
 * @author Fabrizio Giustina
 * @version $Id: TagreferenceMojo.java 217 2014-08-15 20:50:32Z fgiust $
 */
@Mojo(name="tagreference")
public class TagreferenceMojo extends AbstractMavenReport
{

    /**
     * Directory containing tld files. Subdirectories are also processed.
     */
    @Parameter(alias="taglib.src.dir", defaultValue="src/main/resources/META-INF")
    private File srcDir;

    /**
     * Whether to parse html in the description of tld info, tags and attributes. The default value is false.
     */
    @Parameter(defaultValue="false")
    private boolean parseHtml;

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
    public String getName(Locale locale)
    {
        return Messages.getString("Tagreference.name"); //$NON-NLS-1$
    }

    /**
     * @see org.apache.maven.reporting.MavenReport#getDescription(java.util.Locale)
     */
    public String getDescription(Locale locale)
    {
        return Messages.getString("Tagreference.description"); //$NON-NLS-1$
    }

    /**
     * Setter for <code>srcDir</code>.
     * @param srcDir The srcDir to set.
     */
    public void setSrcDir(File srcDir)
    {
        this.srcDir = srcDir;
    }

    /**
     * Setter for <code>outputDirectory</code>.
     * @param outputDirectory The outputDirectory to set.
     */
    public void setOutputDirectory(File outputDirectory)
    {
        this.outputDirectory = outputDirectory;
    }

    /**
     * Setter for <code>project</code>.
     * @param project The project to set.
     */
    public void setProject(MavenProject project)
    {
        this.project = project;
    }

    /**
     * @see org.apache.maven.reporting.AbstractMavenReport#executeReport(java.util.Locale)
     */
    @Override
    protected void executeReport(Locale locale) throws MavenReportException
    {
        if (!srcDir.isDirectory())
        {
            throw new MavenReportException(MessageFormat.format(
                Messages.getString("Taglib.notadir"), new Object[]{srcDir //$NON-NLS-1$
                    .getAbsolutePath()}));
        }

        getLog().debug(
            MessageFormat.format(Messages.getString("Taglib.validating"), new Object[]{srcDir.getAbsolutePath()})); //$NON-NLS-1$

        DocumentBuilder builder;

        try
        {
            builder = XmlHelper.getDocumentBuilder();
        }
        catch (MojoExecutionException e)
        {
            throw new MavenReportException(e.getMessage(), e);
        }
        List<File> tlds;
        try
        {
            tlds = FileUtils.getFiles(srcDir, "**/*.tld", null);
        }
        catch (IOException e)
        {
            throw new MavenReportException(e.getMessage(), e);
        }
        List<Tld> tldList = new ArrayList<>();
        for (Iterator<File> i = tlds.iterator(); i.hasNext();)
        {
            File current = i.next();

            Document tldDoc;
            try
            {
                tldDoc = builder.parse(current);
            }
            catch (Exception e)
            {
                throw new MavenReportException(MessageFormat.format(Messages.getString("Taglib.errorwhileparsing"), //$NON-NLS-1$
                    new Object[]{current.getAbsolutePath()}), e);

            }

            Tld tld = TldParser.parse(tldDoc, current.getName());
            tldList.add(tld);

        }

        if (tldList.size() == 0)
        {
            getLog().info(
                MessageFormat.format(Messages.getString("Taglib.notldfound"), new Object[]{srcDir.getAbsolutePath()})); //$NON-NLS-1$
            return;
        }

        new TagreferenceRenderer(
            getSink(),
            locale,
            tldList.toArray(new Tld[tldList.size()]),
            parseHtml,
            getLog()).render();

    }

    /**
     * @see org.apache.maven.reporting.AbstractMavenReport#canGenerateReport()
     */
    @Override
    public boolean canGenerateReport()
    {
        if (!srcDir.isDirectory())
        {
            return false;
        }

        try
        {
            return FileUtils.getFiles(srcDir, "**/*.tld", null).size() > 0;
        }
        catch (IOException e)
        {
            getLog().error(e.getMessage(), e);
        }
        return false;

    }

}