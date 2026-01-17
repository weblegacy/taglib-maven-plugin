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

package io.github.weblegacy.maven.plugin.taglib.util;

import java.util.Locale;

/**
 * Name of all specific namespaces for the JavaEE-/JakartaEE-Enviroment. It specified namespace
 * {@code javax} (till JavaEE 8} and {@code jakarta} (since JakarteEE 9).
 *
 * @author Stefan Graff
 */
public enum JspNamespace {
    /**
     * {@code javax}-namespace till JavaEE 8.
     */
    JAVAX,
    /**
     * {@code jakarta}-namespace since JakartaEE 9.
     */
    JAKARTA;

    /**
     * Jsp-Namespace for this ENum.
     */
    private final String jspName;

    /**
     * Constructor for this ENum.
     */
    JspNamespace() {
        this.jspName = name().toLowerCase(Locale.ROOT);
    }

    /**
     * Returns the Jsp-Namespace for this ENum.
     *
     * @return the Jsp-Namespace for this ENum
     */
    public String getJspName() {
        return jspName;
    }
}
