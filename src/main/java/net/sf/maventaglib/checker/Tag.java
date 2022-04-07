/**
 *
 *  Copyright 2004-2010 Fabrizio Giustina.
 *
 *  Licensed under the Artistic License; you may not use this file
 *  except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://maven-taglib.sourceforge.net/license.html
 *
 *  THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 *  WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */

package net.sf.maventaglib.checker;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


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