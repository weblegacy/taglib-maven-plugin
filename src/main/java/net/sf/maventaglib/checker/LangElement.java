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

import java.util.Objects;

/**
 * Contains an element with an language-attribute.
 *
 * @author ste-gr
 */
public class LangElement extends IdElement {

    /**
     * The language of the element.
     */
    private final String lang;

    /**
     * Constructs this class with the own builder.
     *
     * @param builder the own builder with all data
     */
    protected LangElement(final Builder builder) {
        super(builder);
        this.lang = builder.lang;
    }

    /**
     * Gets the language of the element
     *
     * @return the langauge of the element
     */
    public String getLang() {
        return this.lang;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.lang);
        hash = 23 * hash + super.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LangElement other = (LangElement) obj;
        return Objects.equals(this.lang, other.lang);
    }

    @Override
    public String toString() {
        return "LangElement{lang=" + this.lang + ", id=" + getId() + '}';
    }

    /**
     * The builder class.
     */
    public static class Builder extends IdElement.Builder {

        /**
         * The language of the element.
         */
        private String lang = "";

        /**
         * Constructor for the builder class.
         */
        public Builder() {
        }

        /**
         * Sets the language of the element in the builder class.
         *
         * @param lang the language of the element
         *
         * @return the builder class
         */
        public Builder lang(final String lang) {
            this.lang = lang;
            return this;
        }

        /**
         * Builds the class from the values of the builder class.
         *
         * @return the builded class
         */
        @Override
        public LangElement build() {
            return new LangElement(this);
        }
    }
}
