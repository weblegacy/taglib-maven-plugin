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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.sf.maventaglib.checker.Tld;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.reporting.MavenReportException;

/**
 * Generates a tag library validation report.
 *
 * @author Fabrizio Giustina
 */
@Mojo(name = "validate")
public class ValidateMojo extends AbstractReportMojoEx {

    @Override
    public String getName(Locale locale) {
        return Messages.getString(locale, "Validate.name");
    }

    @Override
    public String getDescription(Locale locale) {
        return Messages.getString(locale, "Validate.description");
    }

    @Override
    public String getOutputName() {
        return "taglibvalidation";
    }

    @Override
    protected void executeReport(Locale locale) throws MavenReportException {
        final List<Tld> tldList = loadTldFiles();

        final List<String> classPathStrings;
        try {
            classPathStrings = this.project.getCompileClasspathElements();
        } catch (DependencyResolutionRequiredException e) {
            throw new MavenReportException(e.getMessage(), e);
        }

        final List<URL> urls = new ArrayList<>(classPathStrings.size());
        for (String classPathString : classPathStrings) {
            try {
                urls.add(new File(classPathString).toURI().toURL());
            } catch (MalformedURLException e) {
                throw new MavenReportException(e.getMessage(), e);
            }
        }

        URLClassLoader projectClassLoader = AccessController.doPrivileged(
                (PrivilegedAction<URLClassLoader>) ()
                -> new URLClassLoader(urls.toArray(new URL[0]), null)
        );

        ValidateRenderer r = new ValidateRenderer(getSink(), locale,
                tldList.toArray(new Tld[0]), getLog(),
                projectClassLoader);

        r.render();
    }
}
