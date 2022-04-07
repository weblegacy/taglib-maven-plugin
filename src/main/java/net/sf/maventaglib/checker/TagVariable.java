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
public class TagVariable
    implements Comparable
{
    private String nameGiven;

    private String nameFromAttribute;

    private String type;

    private String scope;

    private String description;

    private boolean deprecated;

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getNameGiven()
    {
        return this.nameGiven;
    }

    public void setNameGiven( String name )
    {
        this.nameGiven = name;
    }

    public String getScope()
    {
        return this.scope;
    }

    public void setScope( String scope )
    {
        this.scope = scope;
    }

    public String getType()
    {
        return this.type;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public String getNameFromAttribute()
    {
        return this.nameFromAttribute;
    }

    public void setNameFromAttribute( String nameFromAttribute )
    {
        this.nameFromAttribute = nameFromAttribute;
    }

    public boolean isDeprecated()
    {
        return this.deprecated;
    }

    public void setDeprecated( boolean deprecated )
    {
        this.deprecated = deprecated;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return new ToStringBuilder( this, ToStringStyle.SHORT_PREFIX_STYLE ).append( "nameGiven", this.nameGiven ) //$NON-NLS-1$
            .append( "description", this.description ).append( "deprecated", this.deprecated ).append( "type", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                                                                                                       this.type )
            .append( "scope", this.scope ).append( "nameFromAttribute", this.nameFromAttribute ).toString(); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * @see java.lang.Comparable#compareTo(Object)
     */
    public int compareTo( Object object )
    {
        TagVariable myClass = (TagVariable) object;
        return new CompareToBuilder().append( this.deprecated, myClass.deprecated ).append( this.nameGiven,
                                                                                            myClass.nameGiven )
            .append( this.nameFromAttribute, myClass.nameFromAttribute ).toComparison();
    }

}