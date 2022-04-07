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

package net.sf.maventaglib;

import java.io.File;
import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * @author fgiust
 * @version $Id: Taglib.java 206 2010-01-31 09:37:21Z fgiust $
 */
public class Taglib implements Serializable
{

    /**
     * Function classes for generating EL functions
     * @parameter
     */
    private String[] functionClasses;

    /**
     * Directories containing tag files
     * @parameter
     */
    private File tagdir;

    /**
     * Taglib description
     * @parameter
     */
    private String description;

    /**
     * Taglib shortName
     * @parameter
     */
    private String shortName;

    /**
     * Taglib uri
     * @parameter
     */
    private String uri;

    /**
     * File name
     * @parameter
     */
    private String outputname;

    /**
     * Returns the functionClasses.
     * @return the functionClasses
     */
    public String[] getFunctionClasses()
    {
        return functionClasses;
    }

    /**
     * Sets the functionClasses.
     * @param functionClasses the functionClasses to set
     */
    public void setFunctionClasses(String[] functionClasses)
    {
        this.functionClasses = functionClasses;
    }

    /**
     * Returns the description.
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets the description.
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Returns the shortName.
     * @return the shortName
     */
    public String getShortName()
    {
        return shortName;
    }

    /**
     * Sets the shortName.
     * @param shortName the shortName to set
     */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    /**
     * Returns the uri.
     * @return the uri
     */
    public String getUri()
    {
        return uri;
    }

    /**
     * Sets the uri.
     * @param uri the uri to set
     */
    public void setUri(String uri)
    {
        this.uri = uri;
    }

    /**
     * Returns the outputname.
     * @return the outputname
     */
    public String getOutputname()
    {
        return outputname;
    }

    /**
     * Sets the outputname.
     * @param outputname the outputname to set
     */
    public void setOutputname(String outputname)
    {
        this.outputname = outputname;
    }

    /**
     * Returns the tagdir.
     * @return the tagdir
     */
    public File getTagdir()
    {
        return tagdir;
    }

    /**
     * Sets the tagdir.
     * @param tagdir the tagdir to set
     */
    public void setTagdir(File tagdir)
    {
        this.tagdir = tagdir;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("description", this.description).append(
            "tagdir",
            this.tagdir).append("shortName", this.shortName).append("outputname", this.outputname).append(
            "uri",
            this.uri).append("functionClasses", this.functionClasses).toString();
    }

}
