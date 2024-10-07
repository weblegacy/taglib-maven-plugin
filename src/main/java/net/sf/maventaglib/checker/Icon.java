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
 * Contains information about a icon.
 *
 * @author ste-gr
 */
public class Icon extends LangElement {

    /**
     * The path to the small icon (16 x 16).
     */
    private final String smallIcon;

    /**
     * The path to the large icon (32 x 32).
     */
    private final String largeIcon;

    /**
     * Constructs this class with the own builder.
     *
     * @param builder the own builder with all data
     */
    protected Icon(final Builder builder) {
        super(builder);
        this.smallIcon = builder.smallIcon;
        this.largeIcon = builder.largeIcon;
    }

    /**
     * Gets the path to the small icon (16 x 16).
     *
     * @return the path to the small icon
     */
    public String getSmallIcon() {
        return this.smallIcon;
    }

    /**
     * Gets the path to the large icon (32 x 32).
     *
     * @return the path to the large icon
     */
    public String getLargeIcon() {
        return this.largeIcon;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + super.hashCode();
        hash = 47 * hash + Objects.hashCode(this.smallIcon);
        hash = 47 * hash + Objects.hashCode(this.largeIcon);
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
        final Icon other = (Icon) obj;
        return Objects.equals(this.smallIcon, other.smallIcon)
                && Objects.equals(this.largeIcon, other.largeIcon);
    }

    @Override
    public String toString() {
        return "Icon{smallIcon=" + smallIcon + ", largeIcon=" + largeIcon + ", lang=" + getLang()
                + ", id=" + getId() + '}';
    }

    /**
     * The builder class.
     */
    public static class Builder extends LangElement.Builder {

        /**
         * The path to the small icon (16 x 16).
         */
        private String smallIcon;

        /**
         * The path to the large icon (32 x 32).
         */
        private String largeIcon;

        /**
         * Constructor for the builder class.
         */
        public Builder() {
        }

        /**
         * Sets the path to the small icon (16 x 16) in the builder class.
         *
         * @param smallIcon the path to the small icon (16 x 16)
         *
         * @return the builder class
         */
        public Builder smallIcon(final String smallIcon) {
            this.smallIcon = smallIcon;
            return this;
        }

        /**
         * Sets the path to the large icon (32 x 32) in the builder class.
         *
         * @param largeIcon the path to the large icon (32 x 32)
         *
         * @return the builder class
         */
        public Builder largeIcon(final String largeIcon) {
            this.largeIcon = largeIcon;
            return this;
        }

        /**
         * Builds the class from the values of the builder class.
         *
         * @return the builded class
         */
        @Override
        public Icon build() {
            return new Icon(this);
        }
    }
}
