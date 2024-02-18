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

import com.sun.tlddoc.GeneratorException;
import com.sun.tlddoc.TLDDocGenerator;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReport;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.plexus.util.FileUtils;

/**
 * Generates taglibdoc documentation.
 *
 * @author Fabrizio Giustina
 */
@Mojo(name = "taglibdoc")
public class TaglibdocMojo extends AbstractMavenReport implements MavenReport {

    /**
     * The title for tlddoc generated documentation.
     */
    @Parameter(defaultValue = "${project.name} Tag library documentation")
    private String title;

    /**
     * The TldDoc output dir.
     */
    @Parameter(defaultValue = "${project.reporting.outputDirectory}/tlddoc")
    private File tldDocDir;

    /**
     * Directory containing tld or tag files. Subdirectories are also processed, unless the
     * "dontRecurseIntoSubdirs" parameter is set.
     */
    @Parameter(alias = "taglib.src.dir", defaultValue = "src/main/resources/META-INF")
    private File srcDir;

    /**
     * If set, only file contained directly in the specified directory are used.
     */
    @Parameter
    private boolean dontRecurseIntoSubdirs;

    /**
     * Directory containing custom xsl files (equivalent to the "-xslt" parameter to tlddoc).
     */
    @Parameter
    private File xsltDir;

    @Override
    public void execute() throws MojoExecutionException {
        getLog().debug(MessageFormat.format(Messages.getString("Taglib.generating.tlddoc"),
                srcDir.getAbsolutePath()));
        TLDDocGenerator generator = new TLDDocGenerator();
        generator.setOutputDirectory(tldDocDir);
        generator.setQuiet(true);
        generator.setWindowTitle(this.title);
        if (xsltDir != null) {
            generator.setXSLTDirectory(xsltDir);
        }

        String searchprefix = dontRecurseIntoSubdirs ? "" : "**/";

        if (!srcDir.isDirectory()) {
            throw new MojoExecutionException(MessageFormat.format(
                    Messages.getString("Taglib.notadir"), srcDir.getAbsolutePath()));
        }

        try {
            // handle tlds
            List<File> tlds = FileUtils.getFiles(srcDir, searchprefix + "*.tld", null);
            for (File tld : tlds) {
                generator.addTLD(tld);
            }

            // handle tag files. Add any directory containing .tag or .tagx files
            List<File> tags = FileUtils.getFiles(srcDir, searchprefix + "*.tag", null);
            tags.addAll(FileUtils.getFiles(srcDir, searchprefix + "*.tagx", null));

            if (!tags.isEmpty()) {
                Set<File> directories = new HashSet<>();
                for (File tag : tags) {
                    directories.add(tag.getParentFile());
                }
                for (File directory : directories) {
                    generator.addTagDir(directory);
                }
            }

        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }

        try {
            generator.generate();
        } catch (GeneratorException e) {
            getLog().error(MessageFormat.format(Messages.getString("Taglib.exception"),
                    e.getClass(), e.getMessage()), e);
        }
    }

    @Override
    protected void executeReport(Locale locale) throws MavenReportException {
        try {
            execute();
        } catch (MojoExecutionException e) {
            throw new MavenReportException(e.getMessage(), e);
        }

    }

    @Override
    public String getOutputName() {
        return "tlddoc/index";
    }

    @Override
    public String getName(Locale locale) {
        return Messages.getString(locale, "Taglibdoc.name");
    }

    @Override
    public String getDescription(Locale locale) {
        return Messages.getString(locale, "Taglibdoc.description");
    }

    @Override
    public boolean isExternalReport() {
        return true;
    }

    @Override
    public boolean canGenerateReport() {
        if (!srcDir.isDirectory()) {
            return false;
        }

        try {
            boolean hasTldFiles = !FileUtils.getFiles(srcDir, "**/*.tld", null).isEmpty();
            boolean hasTagFiles = !FileUtils.getFiles(srcDir, "**/*.tag", null).isEmpty();
            return hasTldFiles || hasTagFiles;
        } catch (IOException e) {
            getLog().error(e.getMessage(), e);
        }
        return false;
    }
}
