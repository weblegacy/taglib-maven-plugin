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
package net.sf.maventaglib.checker;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * Contains information about a single tag library.
 * @author Fabrizio Giustina
 * @version $Revision $ ($Author $)
 */
public class Tld
{

    /**
     * tag library shortname.
     */
    private String name;

    /**
     * tag library shortname.
     */
    private String shortname;

    /**
     * tld file name.
     */
    private String filename;

    /**
     * tlibversion.
     */
    private String tlibversion;

    /**
     * taglib uri.
     */
    private String uri;

    /**
     * info/description.
     */
    private String info;

    /**
     * List of tags.
     */
    private Tag[] tags;

    /**
     * List of tagfiles.
     */
    private TagFile[] tagfiles;

    /**
     * List of EL Functions
     */
    private ELFunction[] functions;

    /**
     * @return Returns the filename.
     */
    public String getFilename()
    {
        return this.filename;
    }

    /**
     * @param file The filename to set.
     */
    public void setFilename( String file )
    {
        this.filename = file;
    }

    /**
     * Returns the shortname for this library.
     * @return shortname for this tag library
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Sets the shortname for this library.
     * @param tagLibName shortname for this tag library.
     */
    public void setName( String tagLibName )
    {
        this.name = tagLibName;
    }

    /**
     * Returnss the list of tags in this tag library.
     * @return list of tags in this tag library
     */
    public Tag[] getTags()
    {
        return this.tags;
    }

    /**
     * Getter for <code>uri</code>.
     * @return Returns the uri.
     */
    public String getUri()
    {
        return this.uri;
    }

    /**
     * Setter for <code>uri</code>.
     * @param uri The uri to set.
     */
    public void setUri( String uri )
    {
        this.uri = uri;
    }

    /**
     * Sets the list of tags in this tag library.
     * @param tagList list of tags in this tag library
     */
    public void setTags( Tag[] tagList )
    {
        this.tags = tagList;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return new ToStringBuilder( this, ToStringStyle.SIMPLE_STYLE ).append( "name", this.name ).append( "tags", //$NON-NLS-1$ //$NON-NLS-2$
                                                                                                           this.tags )
            .toString();
    }

    /**
     * Getter for <code>shortname</code>.
     * @return Returns the shortname.
     */
    public String getShortname()
    {
        return this.shortname;
    }

    /**
     * Setter for <code>shortname</code>.
     * @param shortname The shortname to set.
     */
    public void setShortname( String shortname )
    {
        this.shortname = shortname;
    }

    /**
     * Getter for <code>info</code>.
     * @return Returns the info.
     */
    public String getInfo()
    {
        return this.info;
    }

    /**
     * Setter for <code>info</code>.
     * @param info The info to set.
     */
    public void setInfo( String info )
    {
        this.info = info;
    }

    /**
     * Getter for <code>tlibversion</code>.
     * @return Returns the tlibversion.
     */
    public String getTlibversion()
    {
        return this.tlibversion;
    }

    /**
     * Setter for <code>tlibversion</code>.
     * @param tlibversion The tlibversion to set.
     */
    public void setTlibversion( String tlibversion )
    {
        this.tlibversion = tlibversion;
    }

    /**
     * Returns the functions.
     * @return the functions
     */
    public ELFunction[] getFunctions()
    {
        return functions;
    }

    /**
     * Sets the functions.
     * @param functions the functions to set
     */
    public void setFunctions(ELFunction[] functions)
    {
        this.functions = functions;
    }

    /**
     * Returns the tagfiles.
     * @return the tagfiles
     */
    public TagFile[] getTagfiles()
    {
        return tagfiles;
    }

    /**
     * Sets the tagfiles.
     * @param tagfiles the tagfiles to set
     */
    public void setTagfiles(TagFile[] tagfiles)
    {
        this.tagfiles = tagfiles;
    }

}