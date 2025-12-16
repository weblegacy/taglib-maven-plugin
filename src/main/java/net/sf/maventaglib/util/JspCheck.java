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

package net.sf.maventaglib.util;

import java.util.Arrays;
import net.sf.maventaglib.Messages;
import org.apache.maven.plugin.logging.Log;

/**
 * Test and saves the spezific tag-classes for the JavaEE-/JakartaEE-Environment.
 *
 * @author Stefan Graff
 */
public class JspCheck {

    /**
     * All loaded tag-classes for all tag-classes in all JavaEE-/JakartaEE-Environments.
     * <ol>
     * <li>{@link JspClass}</li>
     * <li>{@link JspNamespace}</li>
     * </ol>
     */
    private final Class<?>[][] clazzes;

    /**
     * Construct and initialize this class. It loads all tag-classes for all namespaces with the
     * given classloader.
     *
     * @param log         for Logging
     * @param classLoader the classloader which is used to load the tag-classes
     */
    public JspCheck(final Log log, final ClassLoader classLoader) {
        final JspNamespace[] jspNamespaces = JspNamespace.values();
        final JspClass[] jspClasses = JspClass.values();

        clazzes = new Class<?>[jspClasses.length][];
        Arrays.setAll(clazzes, x -> new Class<?>[jspNamespaces.length]);

        boolean anyNamespaceLoaded = false;

        for (JspNamespace jspNamespace : jspNamespaces) {
            anyNamespaceLoaded |= loadClasses(log, classLoader, jspClasses, jspNamespace);
        }

        // test if any classes are loaded
        if (!anyNamespaceLoaded) {
            log.error(Messages.getString("Validate.error.unabletoload"));
        }
    }

    /**
     * Load all tag-classes with the given classloader and namespace.
     *
     * @param log          for Logging
     * @param classLoader  the classloader which is used to load the tag-classes
     * @param jspClasses   Array with all JSP-Classes (@code JspClass.values()})
     * @param jspNamespace the given namespace
     *
     * @return {@code true} when at least one class is loaded
     */
    private boolean loadClasses(final Log log, final ClassLoader classLoader,
            final JspClass[] jspClasses, final JspNamespace jspNamespace) {

        boolean namespaceLoaded = false;

        final StringBuilder sb = new StringBuilder(48);
        sb.append(jspNamespace.getJspName()).append(".servlet.jsp.tagext.");
        final int len = sb.length();

        // Test to load all classes.
        for (JspClass jspClass : jspClasses) {
            sb.append(jspClass.getJspName());

            try {
                clazzes[jspClass.ordinal()][jspNamespace.ordinal()]
                        = Class.forName(sb.toString(), true, classLoader);
                namespaceLoaded = true;
            } catch (ClassNotFoundException e) {
                log.debug(e);
            }

            sb.setLength(len);
        }

        // Check if all classes loaded.
        if (namespaceLoaded) {
            for (JspClass jspClass : jspClasses) {
                if (clazzes[jspClass.ordinal()][jspNamespace.ordinal()] == null) {
                    final String msg = Messages.getString("Validate.error.unabletoload."
                            + jspNamespace.getJspName() + '.' + jspClass.getJspName());

                    if (jspClass == JspClass.SIMPLE_TAG && jspNamespace == JspNamespace.JAVAX) {
                        log.info(msg);
                    } else {
                        log.error(msg);
                    }
                }
            }
        }

        return namespaceLoaded;
    }

    /**
     * Checks if the specific class is assignable to the loaded classes.
     *
     * @param jspClass the specific class
     * @param clazz    the test-class
     *
     * @return {@code true} specific class is assignable to the test-class
     */
    public boolean check(final JspClass jspClass, final Class<?> clazz) {
        final Class<?>[] jspNamespaceClazzes = clazzes[jspClass.ordinal()];

        for (Class<?> jspClazz : jspNamespaceClazzes) {
            if (jspClazz != null && jspClazz.isAssignableFrom(clazz)) {
                return true;
            }
        }

        return false;
    }
}
