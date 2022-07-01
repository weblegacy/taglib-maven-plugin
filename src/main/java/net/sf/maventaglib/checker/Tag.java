/*
 * The MIT License
 * Copyright © 2004-2014 Fabrizio Giustina
 * Copyright © 2022-2022 Web-Legacy
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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * Contains information about a tag.
 * @author Fabrizio Giustina
 * @version $Revision $ ($Author $)
 */
public class Tag extends TldItem
{

    private String tagClass;

    private String teiClass;

    private String bodycontent;

    private TagAttribute[] attributes;

    private TagVariable[] variables;

    public TagAttribute[] getAttributes()
    {
        return this.attributes;
    }

    public void setAttributes(TagAttribute[] tagAttributes)
    {
        this.attributes = tagAttributes;
    }

    public String getTagClass()
    {
        return this.tagClass;
    }

    public void setTagClass(String className)
    {
        this.tagClass = className;
    }

    public String getTeiClass()
    {
        return this.teiClass;
    }

    public void setTeiClass(String className)
    {
        this.teiClass = className;
    }

    public String getBodycontent()
    {
        return this.bodycontent;
    }

    public void setBodycontent(String bodycontent)
    {
        this.bodycontent = bodycontent;
    }

    public TagVariable[] getVariables()
    {
        return this.variables;
    }

    public void setVariables(TagVariable[] variables)
    {
        this.variables = variables;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("name", this.getName()) //$NON-NLS-1$
            .append("tagClass", this.tagClass).append("teiClass", this.teiClass).append("attributes", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                this.attributes)
            .toString();
    }

}