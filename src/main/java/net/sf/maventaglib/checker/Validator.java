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
import java.util.List;

/**
 * Contains information about a single tag library.
 *
 * @author Fabrizio Giustina
 */
public class Validator extends IdElement {

    /**
     * The description of the validator. Can contain several languages.
     */
    private final LangElements<StringElement> description;

    /**
     * Defines the TagLibraryValidator class that can be used to validate the conformance of a JSP
     * page to using this tag library.
     */
    private final StringId validatorClass;

    /**
     * The init-parameter elements contains a name/value pair as an initialization parameter.
     */
    private final List<Parameter> initParams;

    /**
     * Constructs this class with the own builder.
     *
     * @param builder the own builder with all data
     */
    protected Validator(final Builder builder) {
        super(builder);
        this.description = builder.description.build();
        this.validatorClass = builder.validatorClass.build();
        this.initParams = Collections.unmodifiableList(builder.initParams);
    }

    /**
     * Gets the description of the validator.
     *
     * @return the description of the parameter
     */
    public LangElements<StringElement> getDescription() {
        return description;
    }

    /**
     * Gets the TagLibraryValidator class that can be used to validate the conformance of a JSP page
     * to using this tag library.
     *
     * @return the TagLibraryValidator class
     */
    public StringId getValidatorClass() {
        return validatorClass;
    }

    /**
     * Gets the init-parameter elements contains a name/value pair as an initialization parameter.
     *
     * @return the init-parameter elements
     */
    public List<Parameter> getInitParams() {
        return initParams;
    }

    /**
     * The builder class.
     */
    public static class Builder extends IdElement.Builder {

        /**
         * The description of the validator. Can contain several languages.
         */
        private LangElements.Builder<StringLang> description
                = new LangElements.Builder<>(StringLang.EMPTY);

        /**
         * Defines the TagLibraryValidator class that can be used to validate the conformance of a
         * JSP page to using this tag library.
         */
        private StringId.Builder validatorClass = new StringId.Builder();

        /**
         * The init-param element contains a name/value pair as an initialization param.
         *
         */
        private ArrayList<Parameter> initParams = new ArrayList<>();

        /**
         * Constructor for the builder class.
         */
        public Builder() {
        }

        /**
         * Sets the description of the validator in the builder class.
         *
         * @param description the description of the validator
         *
         * @return the builder class
         */
        public Builder description(final LangElements.Builder<StringLang> description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the TagLibraryValidator class in the builder class.
         *
         * @param validatorClass the TagLibraryValidator class
         *
         * @return the builder class
         */
        public Builder description(final StringId.Builder validatorClass) {
            this.validatorClass = validatorClass;
            return this;
        }

        /**
         * Adds an new init-parameter in the builder class.
         *
         * @param initParam the new init-parameter
         *
         * @return the builder class
         */
        public Builder addInitParam(final Parameter initParam) {
            initParams.add(initParam);
            return this;
        }

        /**
         * Builds the class from the values of the builder class.
         *
         * @return the builded class
         */
        @Override
        public Validator build() {
            return new Validator(this);
        }
    }
}
