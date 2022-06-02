/*
 * The MIT License
 * Copyright © 2004-2014 Fabrizio Giustina
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
package net.sf.maventaglib;

import java.io.File;
import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.maven.plugins.annotations.Parameter;


/**
 * @author fgiust
 * @version $Id: Taglib.java 217 2014-08-15 20:50:32Z fgiust $
 */
public class Taglib implements Serializable
{
    private static final long serialVersionUID = -1130286008090091787L;

    /**
     * Function classes for generating EL functions
     */
    @Parameter
    private String[] functionClasses;

    /**
     * Directories containing tag files
     */
    @Parameter
    private File tagdir;

    /**
     * Taglib description
     */
    @Parameter
    private String description;

    /**
     * Taglib shortName
     */
    @Parameter
    private String shortName;

    /**
     * Taglib uri
     */
    @Parameter
    private String uri;

    /**
     * File name
     */
    @Parameter
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
