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
package net.sf.maventaglib.checker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Contains elements in several languages.
 *
 * @param <T> type of the element
 *
 * @author ste-gr
 */
public class LangElements<T extends LangElement> {

    /**
     * The elements in several languages.
     */
    private final Map<String, T> langElements;

    /**
     * The element in default language.
     */
    private final T defaultLangElement;

    /**
     * Cache the hash code for the language-element.
     */
    private int hash; // Default to 0

    /**
     * Cache if the hash has been calculated as actually being zero, enabling us to avoid
     * recalculating this.
     */
    private boolean hashIsZero; // Default to false;

    /**
     * Constructs this class with the own builder.
     *
     * @param builder the own builder with all data
     */
    protected LangElements(final Builder<T> builder) {
        final Map<String, T> elements = new LinkedHashMap<>();
        for (T element : builder.langElements) {
            elements.put(element.getLang(), element);
        }
        this.langElements = Collections.unmodifiableMap(elements);

        if (builder.langElements.isEmpty()) {
            defaultLangElement = builder.defaultElement;
        } else if (langElements.size() == 1) {
            defaultLangElement = builder.langElements.get(0);
        } else if (elements.containsKey("en")) {
            defaultLangElement = elements.get("en");
        } else if (elements.containsKey("")) {
            defaultLangElement = elements.get("");
        } else {
            defaultLangElement = builder.langElements.get(0);
        }
    }

    /**
     * Gets the elements in several languages.
     *
     * @return the elements in several languages
     */
    public Map<String, T> getLangElements() {
        return this.langElements;
    }

    /**
     * Gets the element in default language.
     *
     * @return the element in default language
     */
    public T getDefaultLangElement() {
        return this.defaultLangElement;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LangElements<?> other = (LangElements<?>) obj;
        return langElements.equals(other.langElements);
    }

    @Override
    public int hashCode() {
        int h = hash;
        if (h == 0 && !hashIsZero) {
            h = langElements.hashCode();
            if (h == 0) {
                hashIsZero = true;
            } else {
                hash = h;
            }
        }
        return h;
    }

    @Override
    public String toString() {
        return "[langElements=" + this.langElements + ", defaultLangElement=" + this.defaultLangElement + ']';
    }

    /**
     * The builder class.
     *
     * @param <T> type of the element
     */
    public static class Builder<T extends LangElement> {

        /**
         * The ID of the element.
         */
        private ArrayList<T> langElements = new ArrayList<>();

        /**
         * The default-value when no elements defined.
         */
        private T defaultElement;

        /**
         * Constructor for the builder class.
         */
        public Builder() {
        }

        /**
         * Constructor for the builder class.
         *
         * @param defaultElement the default-value when no elements defined
         */
        public Builder(final T defaultElement) {
            this.defaultElement = defaultElement;
        }

        /**
         * Sets the default-value in the builder class.
         *
         * @param defaultElement the default-value when no elements defined
         *
         * @return the builder class
         */
        public Builder<T> defaultElement(final T defaultElement) {
            this.defaultElement = defaultElement;
            return this;
        }

        /**
         * Adds an new element in the builder class.
         *
         * @param element the new element
         *
         * @return the builder class
         */
        public Builder<T> addLangElement(final T element) {
            langElements.add(element);
            return this;
        }

        /**
         * Builds the class from the values of the builder class.
         *
         * @return the builded class
         */
        public LangElements<T> build() {
            return new LangElements<>(this);
        }
    }
}
