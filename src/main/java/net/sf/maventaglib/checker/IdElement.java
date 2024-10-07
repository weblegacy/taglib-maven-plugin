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
 * Contains the ID for an element.
 *
 * @author ste-gr
 */
public class IdElement {

    /**
     * The ID of the element.
     */
    private final String id;

    /**
     * Constructs this class with the own builder.
     *
     * @param builder the own builder with all data
     */
    protected IdElement(final Builder builder) {
        this.id = builder.id;
    }

    /**
     * Gets the ID of the element.
     *
     * @return the ID of the element
     */
    public String getId() {
        return this.id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.id);
        return hash;
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
        final IdElement other = (IdElement) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "IdElement{id=" + this.id + '}';
    }

    /**
     * The builder class.
     */
    public static class Builder {

        /**
         * The ID of the element.
         */
        private String id;

        /**
         * Constructor for the builder class.
         */
        public Builder() {
        }

        /**
         * Sets the ID of the element in the builder class.
         *
         * @param id the ID of the element
         *
         * @return the builder class
         */
        public Builder id(final String id) {
            this.id = id;
            return this;
        }

        /**
         * Builds the class from the values of the builder class.
         *
         * @return the builded class
         */
        public IdElement build() {
            return new IdElement(this);
        }
    }
}
