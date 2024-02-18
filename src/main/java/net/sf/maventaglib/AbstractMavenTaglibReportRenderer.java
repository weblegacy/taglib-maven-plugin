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

import java.util.Locale;
import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.reporting.AbstractMavenReportRenderer;

/**
 * An abstract class to manage report generation of this plugin.
 *
 * @author ste-gr
 */
public abstract class AbstractMavenTaglibReportRenderer extends AbstractMavenReportRenderer {

    /**
     * The defined locale for the messages.
     */
    private final Locale locale;

    /**
     * The class-constructor.
     *
     * @param sink   the sink to use.
     * @param locale the wanted locale to return the report's description, could be {@code null}.
     */
    protected AbstractMavenTaglibReportRenderer(Sink sink, Locale locale) {
        super(sink);
        this.locale = locale;
    }

    /**
     * Gets a string for the given key from the resource bundle with the defined locale. If the
     * locale is {@code null}, then the default-locale is used.
     *
     * @param key the key for the desired string
     *
     * @exception NullPointerException if {@code key} is {@code null}
     * @exception ClassCastException   if the object found for the given key is not a string
     *
     * @return the string for the given key with the defined locale
     */
    protected String getMessageString(String key) {
        return Messages.getString(locale, key);
    }
}
