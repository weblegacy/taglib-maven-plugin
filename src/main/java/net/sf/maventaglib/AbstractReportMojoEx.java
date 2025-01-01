/*
 * The MIT License
 * Copyright © 2004-2014 Fabrizio Giustina
 * Copyright © 2022-2025 Web-Legacy
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
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import net.sf.maventaglib.checker.Tld;
import net.sf.maventaglib.checker.TldParser;
import net.sf.maventaglib.util.XmlHelper;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.plexus.util.FileUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * An extended abstract class for the {@code Mojo}s of this plugin.
 *
 * @author ste-gr
 */
public abstract class AbstractReportMojoEx extends AbstractReportMojo {

    /**
     * Directory containing tld files. Subdirectories are also processed, unless the
     * {@code dontRecurseIntoSubdirs} parameter is set.
     */
    @Parameter(alias = "taglib.src.dir", defaultValue = "src/main/resources/META-INF")
    protected File srcDir;

    /**
     * Loads all tld files from the {@code srcDir}.
     *
     * @return all loaded tld files
     *
     * @throws MavenReportException if an error occurs during execution
     */
    protected List<Tld> loadTldFiles() throws MavenReportException {
        if (!srcDir.isDirectory()) {
            throw new MavenReportException(MessageFormat.format(
                    Messages.getString("Taglib.notadir"), srcDir.getAbsolutePath()));
        }

        getLog().debug(MessageFormat.format(
                Messages.getString("Taglib.validating"), srcDir.getAbsolutePath()));

        final DocumentBuilder builder;
        try {
            builder = XmlHelper.getDocumentBuilder();
        } catch (MojoExecutionException e) {
            throw new MavenReportException(e.getMessage(), e);
        }

        final String searchprefix = dontRecurseIntoSubdirs ? "" : "**/";

        final List<File> tlds;
        try {
            tlds = FileUtils.getFiles(srcDir, searchprefix + "*.tld", null);
        } catch (IOException e) {
            throw new MavenReportException(e.getMessage(), e);
        }

        final List<Tld> tldList = new ArrayList<>(tlds.size());
        for (File current : tlds) {
            final Document tldDoc;
            try {
                tldDoc = builder.parse(current);
            } catch (IOException | SAXException e) {
                throw new MavenReportException(MessageFormat.format(
                        Messages.getString("Taglib.errorwhileparsing"),
                        current.getAbsolutePath()), e);
            }

            final Tld tld = TldParser.parse(tldDoc, current.getName());
            tldList.add(tld);
        }

        if (tldList.isEmpty()) {
            getLog().info(MessageFormat.format(
                    Messages.getString("Taglib.notldfound"), srcDir.getAbsolutePath()));
        }

        return tldList;
    }

    @Override
    public boolean canGenerateReport() {
        return hasFiles(srcDir, "tld");
    }
}
