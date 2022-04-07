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


/**
 * @author fgiust
 * @version $Id: TldItem.java 206 2010-01-31 09:37:21Z fgiust $
 */
public class TldItem implements Comparable
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
    public int compareTo(Object object)
    {
        TldItem myClass = (TldItem) object;
        return new CompareToBuilder()
            .append(this.deprecated, myClass.deprecated)
            .append(this.name, myClass.name)
            .toComparison();
    }
}
