/*
 * The MIT License
 * Copyright © 2004-2014 Fabrizio Giustina
 * Copyright © 2022-2024 Web-Legacy
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

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Contains information about a single tag library.
 *
 * @author Fabrizio Giustina
 */
public class Tld {

    /**
     * The tld file name.
     */
    private final String filename;

    /**
     * A simple string describing the "use" of this taglib, should be user discernable.
     */
    private final LangElements<StringLang> description;

    /**
     * The display-name element contains a short name that is intended to be displayed by tools.
     */
    private final LangElements<StringLang> displayName;

    /**
     * Optional icons that can be used by tools
     */
    private final LangElements<Icon> icons;

    /**
     * The version of the tag library implementation.
     */
    private final String tlibVersion;

    /**
     * A simple default short name that could be used by a JSP authoring tool to create names with a
     * mnemonic value; for example, the it may be used as the preferred prefix value in taglib
     * directives.
     */
    private final StringId shortName;

    /**
     * A URI uniquely identifying this taglib.
     */
    private final StringId uri;

    /**
     * Optional TagLibraryValidator information.
     */
    private final String validator;

    ddd /**
             * The info/description.
             */
    private final String info;

    /**
     * Optional event listener specification.
     */
    private final String listener;

    /**
     * Tags in this tag library.
     */
    private List<Tag> tags;

    /**
     * Tag files in this tag library.
     */
    private List<TagFile> tagfiles;

    /**
     * Zero or more EL functions defined in this tag library.
     */
    private List<ElFunction> functions;

    /**
     * Zero or more extensions that provide extra information about this taglib, for tool
     * consumption.
     */
    private List<String> taglibExtension;

    /**
     *
     * @param name
     * @param shortname
     * @param filename
     * @param tlibversion
     * @param uri
     * @param info
     * @param tags
     * @param tagfiles
     * @param functions
     */
    public Tld(LangElement<String> description, LangElement<String> displayName, LangElement<Icon> icon, String name, String shortname, String filename, String tlibversion, String uri, String info, List<Tag> tags, List<TagFile> tagfiles, List<ElFunction> functions) {
        this.description = description;
        this.displayName = displayName;
        this.icon = icon;
        this.name = name;
        this.shortname = shortname;
        this.filename = filename;
        this.tlibversion = tlibversion;
        this.uri = uri;
        this.info = info;
        this.tags = tags;
        this.tagfiles = tagfiles;
        this.functions = functions;
    }

    /**
     * Gets the filename.
     *
     * @return Returns the filename.
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     * Returns the shortname for this library.
     *
     * @return shortname for this tag library
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the list of tags in this tag library.
     *
     * @return list of tags in this tag library
     */
    public List<Tag> getTags() {
        return this.tags;
    }

    /**
     * Getter for {@code uri}.
     *
     * @return Returns the uri.
     */
    public String getUri() {
        return this.uri;
    }

    /**
     * Getter for {@code shortname}.
     *
     * @return Returns the shortname.
     */
    public String getShortname() {
        return this.shortname;
    }

    /**
     * Getter for {@code info}.
     *
     * @return Returns the info.
     */
    public String getInfo() {
        return this.info;
    }

    /**
     * Getter for {@code tlibversion}.
     *
     * @return Returns the tlibversion.
     */
    public String getTlibversion() {
        return this.tlibversion;
    }

    /**
     * Returns the functions.
     *
     * @return the functions
     */
    public List<ElFunction> getFunctions() {
        return functions;
    }

    /**
     * Returns the tagfiles.
     *
     * @return the tagfiles
     */
    public List<TagFile> getTagfiles() {
        return tagfiles;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("name", this.name)
                .append("tags", this.tags)
                .toString();
    }
}
