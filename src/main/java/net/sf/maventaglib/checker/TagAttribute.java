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

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Javabean representing a tag attribute.
 * @author Fabrizio Giustina
 * @version $Revision: 206 $ ($Author: fgiust $)
 */
public class TagAttribute
    implements Comparable
{

    private String name;

    private String type;

    private String description;

    private boolean required;

    private boolean deprecated;

    private boolean rtexprvalue;

    public String getName()
    {
        return this.name;
    }

    public void setName( String attributeName )
    {
        this.name = attributeName;
    }

    public String getType()
    {
        return this.type;
    }

    public void setType( String attributeType )
    {
        this.type = attributeType;
    }

    public boolean isDeprecated()
    {
        return this.deprecated;
    }

    public void setDeprecated( boolean deprecated )
    {
        this.deprecated = deprecated;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public boolean isRequired()
    {
        return this.required;
    }

    public void setRequired( boolean required )
    {
        this.required = required;
    }

    public boolean isRtexprvalue()
    {
        return this.rtexprvalue;
    }

    public void setRtexprvalue( boolean rtexprvalue )
    {
        this.rtexprvalue = rtexprvalue;
    }

    /**
     * @see java.lang.Comparable#compareTo(Object)
     */
    public int compareTo( Object object )
    {
        TagAttribute myClass = (TagAttribute) object;
        return new CompareToBuilder().append( this.deprecated, myClass.deprecated ).append( this.name, myClass.name )
            .toComparison();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return new ToStringBuilder( this, ToStringStyle.SHORT_PREFIX_STYLE ).append( "name", this.name ) //$NON-NLS-1$
            .append( "description", this.description ).append( "deprecated", this.deprecated ).append( "type", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                                                                                                       this.type )
            .append( "required", this.required ).toString(); //$NON-NLS-1$
    }

}