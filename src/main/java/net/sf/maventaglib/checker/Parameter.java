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
 * Contains parameter (name/value) informations.
 *
 * @author Fabrizio Giustina
 */
public class Parameter extends IdElement {

    /**
     * The description of the parameter. Can contain several languages.
     */
    private final LangElements<StringLang> description;

    /**
     * The name of the parameter.
     */
    private final StringId paramName;

    /**
     * The value of the parameter.
     */
    private final StringId paramValue;

    /**
     * Constructs this class with the own builder.
     *
     * @param builder the own builder with all data
     */
    protected Parameter(final Builder builder) {
        super(builder);
        this.description = builder.description.build();
        this.paramName = builder.paramName.build();
        this.paramValue = builder.paramValue.build();
    }

    /**
     * Gets the description of the parameter.
     *
     * @return the description of the parameter
     */
    public LangElements<StringElement> getDescription() {
        return this.description;
    }

    /**
     * Gets the name of the parameter.
     *
     * @return the name of the parameter
     */
    public StringId getParamName() {
        return this.paramName;
    }

    /**
     * Gets the value of the parameter.
     *
     * @return the value of the parameter
     */
    public StringId getParamValue() {
        return this.paramValue;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + super.hashCode();
        hash = 89 * hash + Objects.hashCode(this.description);
        hash = 89 * hash + Objects.hashCode(this.paramName);
        hash = 89 * hash + Objects.hashCode(this.paramValue);
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
        final Parameter other = (Parameter) obj;
        return Objects.equals(this.description, other.description)
                && Objects.equals(this.paramName, other.paramName)
                && Objects.equals(this.paramValue, other.paramValue);
    }

    @Override
    public String toString() {
        return "Parameter{description=" + description + ", paramName=" + paramName
                + ", paramValue=" + paramValue + ", id=" + getId() + '}';
    }

    /**
     * The builder class.
     */
    public static class Builder extends IdElement.Builder {

        /**
         * The description of the parameter. Can contain several languages.
         */
        private LangElements.Builder<StringLang> description
                = new LangElements.Builder<>(StringLang.EMPTY);

        /**
         * The name of the parameter.
         */
        private StringId.Builder paramName = new StringId.Builder();

        /**
         * The value of the parameter.
         */
        private StringId.Builder paramValue = new StringId.Builder();

        /**
         * Constructor for the builder class.
         */
        public Builder() {
        }

        /**
         * Sets the description of the parameter in the builder class.
         *
         * @param description the description of the parameter
         *
         * @return the builder class
         */
        public Builder description(final LangElements.Builder<StringLang> description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the name of the parameter in the builder class.
         *
         * @param paramName the name of the parameter
         *
         * @return the builder class
         */
        public Builder paramName(final StringId.Builder paramName) {
            this.paramName = paramName;
            return this;
        }

        /**
         * Sets the value of the parameter in the builder class.
         *
         * @param paramValue the value of the parameter
         *
         * @return the builder class
         */
        public Builder paramValue(final StringId.Builder paramValue) {
            this.paramValue = paramValue;
            return this;
        }

        /**
         * Builds the class from the values of the builder class.
         *
         * @return the builded class
         */
        @Override
        public Parameter build() {
            return new Parameter(this);
        }
    }
}
