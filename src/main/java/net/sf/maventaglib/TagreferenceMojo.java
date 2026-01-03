/*
 * The MIT License
 * Copyright © 2004-2014 Fabrizio Giustina
 * Copyright © 2022-2026 Web-Legacy
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

import java.util.List;
import java.util.Locale;
import net.sf.maventaglib.checker.Tld;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.reporting.MavenReportException;

/**
 * Generates a tag reference xdoc that can be integrated in a maven generated site.
 *
 * @author Fabrizio Giustina
 */
@Mojo(name = "tagreference")
public class TagreferenceMojo extends AbstractReportMojoEx {

    /**
     * Whether to parse html in the description of tld info, tags and attributes. The default value
     * is false.
     */
    @Parameter(defaultValue = "false")
    private boolean parseHtml;

    @Override
    public String getOutputName() {
        return "tagreference";
    }

    @Override
    public String getName(Locale locale) {
        return Messages.getString(locale, "Tagreference.name");
    }

    @Override
    public String getDescription(Locale locale) {
        return Messages.getString(locale, "Tagreference.description");
    }

    @Override
    protected void executeReport(Locale locale) throws MavenReportException {
        final List<Tld> tldList = loadTldFiles();

        new TagreferenceRenderer(getSink(), locale, tldList.toArray(Tld[]::new), parseHtml,
                getLog()).render();
    }
}
