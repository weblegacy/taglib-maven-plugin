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

package io.github.weblegacy.maven.plugin.taglib;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Classes to manage the i18n-messages.
 *
 * @author Fabrizio Giustina
 */
public class Messages {

    /**
     * The name of the resource-bundle.
     */
    private static final String BUNDLE_NAME = "m2-taglib";

    /**
     * The resource-bundle with all locals.
     */
    private static final ConcurrentHashMap<String, ResourceBundle> RESOURCE_BUNDLES
            = new ConcurrentHashMap<>();

    /**
     * The resource-bundle with default-locale.
     */
    private static final ResourceBundle DFLT_RESOURCE_BUNDLE;

    static {
        DFLT_RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
        RESOURCE_BUNDLES.put(DFLT_RESOURCE_BUNDLE.getLocale().toString(), DFLT_RESOURCE_BUNDLE);
    }

    /**
     * Privat class-constructor.
     */
    private Messages() {
    }

    /**
     * Gets a string for the given key from the resource bundle with the default-locale.
     *
     * @param key the key for the desired string
     *
     * @exception NullPointerException if {@code key} is {@code null}
     * @exception ClassCastException   if the object found for the given key is not a string
     *
     * @return the string for the given key with the default-locale
     */
    public static String getString(String key) {
        return getString(DFLT_RESOURCE_BUNDLE, key);
    }

    /**
     * Gets a string for the given key from the resource bundle with the given locale. If the locale
     * is {@code null}, then the default-locale is used.
     *
     * @param locale the resource-bundle with the wanted locale
     * @param key    the key for the desired string
     *
     * @exception NullPointerException if {@code key} is {@code null}
     * @exception ClassCastException   if the object found for the given key is not a string
     *
     * @return the string for the given key with the given locale
     */
    public static String getString(Locale locale, String key) {
        final ResourceBundle resourceBundle = locale == null
                ? DFLT_RESOURCE_BUNDLE
                : RESOURCE_BUNDLES.computeIfAbsent(locale.toString(),
                        k -> ResourceBundle.getBundle(BUNDLE_NAME, locale));
        return getString(resourceBundle, key);
    }

    /**
     * Gets a string for the given key from the given resource bundle.
     *
     * @param resourceBundle the resource-bundle with the wanted locale
     * @param key            the key for the desired string
     *
     * @exception NullPointerException if {@code key} is {@code null}
     * @exception ClassCastException   if the object found for the given key is not a string
     *
     * @return the string for the given key
     */
    private static String getString(ResourceBundle resourceBundle, String key) {
        try {
            return resourceBundle == null
                    ? '!' + key + '!'
                    : resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
