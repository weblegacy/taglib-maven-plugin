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

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Name of all specific tag-classes for the JavaEE-/JakartaEE-Enviroment. It is specified without
 * the prefix {@code javax} (till JavaEE 8} and {@code jakarta} (since JakarteEE 9).
 *
 * @author Stefan Graff
 */
public enum JspClass {
    /**
     * Jsp-Class for {@code TagSupport}.
     */
    TAG_SUPPORT,
    /**
     * Jsp-Class for {@code TagExtraInfo}.
     */
    TAG_EXTRA_INFO,
    /**
     * Jsp-Class for {@code SimpleTag}.
     */
    SIMPLE_TAG;

    /**
     * Jsp-Class-Name for this ENum.
     */
    private final String jspName;

    /**
     * Constructor for this ENum.
     */
    JspClass() {
        // Convert snake_case to CamelCase
        this.jspName = Arrays.stream(name().toLowerCase(Locale.ROOT).split("_"))
                .map(
                        s -> Character.toUpperCase(s.charAt(0))
                        + s.substring(1))
                .collect(Collectors.joining());
    }

    /**
     * Returns the Jsp-Class-Name for this ENum.
     *
     * @return the Jsp-Class-Name for this ENum
     */
    public String getJspName() {
        return jspName;
    }
}
