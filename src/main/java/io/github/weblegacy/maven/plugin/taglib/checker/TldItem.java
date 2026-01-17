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

package io.github.weblegacy.maven.plugin.taglib.checker;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Java-Bean representing a tld item.
 *
 * @author fgiust
 */
public class TldItem implements Comparable<TldItem> {

    /**
     * The name of the tld item.
     */
    private String name;

    /**
     * The description of the tld item.
     */
    private String description;

    /**
     * The example of the tld item.
     */
    private String example;

    /**
     * If the tld item is deprecated.
     */
    private boolean deprecated;

    public String getName() {
        return this.name;
    }

    public void setName(String tagName) {
        this.name = tagName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExample() {
        return this.example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public boolean isDeprecated() {
        return this.deprecated;
    }

    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }

    @Override
    public int compareTo(TldItem object) {
        if (object == this) {
            return 0;
        }

        if (object.getClass() != getClass()) {
            throw new ClassCastException("compareTo with different classes: "
                    + this.getClass().getName()
                    + " <> "
                    + object.getClass().getName());
        }

        return new CompareToBuilder()
                .append(this.deprecated, object.deprecated)
                .append(this.name, object.name)
                .toComparison();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (obj.getClass() != getClass()) {
            return false;
        }

        TldItem rhs = (TldItem) obj;
        return new EqualsBuilder()
                .append(this.deprecated, rhs.deprecated)
                .append(this.name, rhs.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.deprecated)
                .append(this.name)
                .toHashCode();
    }
}
