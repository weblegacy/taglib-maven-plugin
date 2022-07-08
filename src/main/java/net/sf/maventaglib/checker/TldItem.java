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

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author fgiust
 * @version $Id: TldItem.java 217 2014-08-15 20:50:32Z fgiust $
 */
public class TldItem implements Comparable<TldItem>
{

    private String name;

    private String description;

    private String example;

    private boolean deprecated;

    public String getName()
    {
        return this.name;
    }

    public void setName(String tagName)
    {
        this.name = tagName;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getExample()
    {
        return this.example;
    }

    public void setExample(String example)
    {
        this.example = example;
    }

    public boolean isDeprecated()
    {
        return this.deprecated;
    }

    public void setDeprecated(boolean deprecated)
    {
        this.deprecated = deprecated;
    }

    /**
     * @see java.lang.Comparable#compareTo(Object)
     */
    public int compareTo(TldItem object)
    {
        if(object == this)
        {
            return 0;
        }

        if(object.getClass() != getClass())
        {
            throw new ClassCastException("compareTo with different classes: " +
                this.getClass().getName() +
                " <> " +
                object.getClass().getName());
        }

        return new CompareToBuilder()
            .append(this.deprecated, object.deprecated)
            .append(this.name, object.name)
            .toComparison();
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if(obj == null)
        {
            return false;
        }

        if(obj == this)
        {
            return true;
        }

        if(obj.getClass() != getClass())
        {
            return false;
        }

        TldItem rhs = (TldItem) obj;
        return new EqualsBuilder()
                .append(this.deprecated, rhs.deprecated)
                .append(this.name, rhs.name)
                .isEquals();
      }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.deprecated)
                .append(this.name)
                .toHashCode();
      }
}