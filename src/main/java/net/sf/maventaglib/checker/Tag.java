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
package net.sf.maventaglib.checker;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Contains information about a tag.
 *
 * @author Fabrizio Giustina
 */
public class Tag extends TldItem {

    /**
     * The tag-class information.
     */
    private String tagClass;

    /**
     * The tei-class information.
     */
    private String teiClass;

    /**
     * The body-content information.
     */
    private String bodycontent;

    /**
     * The attributs of the tag.
     */
    private TagAttribute[] attributes;

    /**
     * The variables of the tag.
     */
    private TagVariable[] variables;

    /**
     * Gets the attributes of the tag.
     *
     * @return the attributes of the tag
     */
    public TagAttribute[] getAttributes() {
        return this.attributes;
    }

    /**
     * Sets the attributes of the tag.
     *
     * @param tagAttributes the attributes of the tag
     */
    public void setAttributes(TagAttribute[] tagAttributes) {
        this.attributes = tagAttributes;
    }

    /**
     * Gets the tag-class of the tag.
     *
     * @return the tag-class of the tag
     */
    public String getTagClass() {
        return this.tagClass;
    }

    /**
     * Sets the tag-class of the tag.
     *
     * @param className the tag-class of the tag
     */
    public void setTagClass(String className) {
        this.tagClass = className;
    }

    /**
     * Gets the tei-class of the tag.
     *
     * @return the tei-class of the tag
     */
    public String getTeiClass() {
        return this.teiClass;
    }

    /**
     * Sets the tei-class of the tag.
     *
     * @param className the tei-class of the tag
     */
    public void setTeiClass(String className) {
        this.teiClass = className;
    }

    public String getBodycontent() {
        return this.bodycontent;
    }

    public void setBodycontent(String bodycontent) {
        this.bodycontent = bodycontent;
    }

    public TagVariable[] getVariables() {
        return this.variables;
    }

    public void setVariables(TagVariable[] variables) {
        this.variables = variables;
    }

    @Override
    public int compareTo(TldItem object) {
        int ret = super.compareTo(object);
        if (ret != 0) {
            return ret;
        }

        Tag rhs = (Tag) object;
        return new CompareToBuilder()
                .append(this.tagClass, rhs.tagClass)
                .append(this.teiClass, rhs.teiClass)
                .append(this.attributes, rhs.attributes)
                .toComparison();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !super.equals(obj)) {
            return false;
        }

        Tag rhs = (Tag) obj;
        return new EqualsBuilder()
                .append(this.tagClass, rhs.tagClass)
                .append(this.teiClass, rhs.teiClass)
                .append(this.attributes, rhs.attributes)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(this.tagClass)
                .append(this.teiClass)
                .append(this.attributes)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("name", this.getName())
                .append("tagClass", this.tagClass)
                .append("teiClass", this.teiClass)
                .append("attributes", this.attributes)
                .toString();
    }
}
