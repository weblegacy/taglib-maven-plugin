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

import java.io.StringReader;
import java.text.MessageFormat;
import java.util.Locale;

import net.sf.maventaglib.checker.ELFunction;
import net.sf.maventaglib.checker.Tag;
import net.sf.maventaglib.checker.TagAttribute;
import net.sf.maventaglib.checker.TagFile;
import net.sf.maventaglib.checker.TagVariable;
import net.sf.maventaglib.checker.Tld;
import net.sf.maventaglib.checker.TldItem;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.doxia.module.xhtml.XhtmlParser;
import org.apache.maven.doxia.parser.ParseException;
import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.reporting.AbstractMavenReportRenderer;


/**
 * Validates tag handler classes fount in tlds.
 * @author Fabrizio Giustina
 * @version $Revision $ ($Author $)
 */
public class TagreferenceRenderer extends AbstractMavenReportRenderer
{

    private static final String OPEN_DIV = "<div>";

    private static final String CLOSE_DIV = "</div>";

    //private Locale locale;

    /**
     * list of Tld to check.
     */
    private Tld[] tlds;

    private boolean parseHtml = true;

    private Log log;

    /**
     * @param sink
     */
    public TagreferenceRenderer(Sink sink, Locale locale, Tld[] tlds, boolean parseHtml, Log log)
    {
        super(sink);

        //this.locale = locale;
        this.tlds = tlds;
        this.parseHtml = parseHtml;
        this.log = log;

    }

    /**
     * @see org.apache.maven.reporting.AbstractMavenReportRenderer#getTitle()
     */
    @Override
    public String getTitle()
    {
        return Messages.getString("Tagreference.title"); //$NON-NLS-1$
    }

    /**
     * @see org.apache.maven.reporting.AbstractMavenReportRenderer#renderBody()
     */
    @Override
    protected void renderBody()
    {
        sink.body();

        startSection(Messages.getString("Tagreference.h1")); //$NON-NLS-1$
        paragraph(Messages.getString("Tagreference.intro")); //$NON-NLS-1$

        sink.list();
        for (int j = 0; j < tlds.length; j++)
        {
            log.debug("Rendering " + tlds[j].getFilename());
            sink.listItem();
            sink.link("#" + tlds[j].getFilename()); //$NON-NLS-1$
            sink.text(MessageFormat.format(Messages.getString("Tagreference.listitem.tld"), new Object[]{ //$NON-NLS-1$
                StringUtils.defaultIfEmpty(tlds[j].getName(), tlds[j].getShortname()), tlds[j].getFilename()}));
            sink.link_();
            sink.text(Messages.getString("Tagreference.listitem.uri") + tlds[j].getUri()); //$NON-NLS-1$
            sink.listItem_();
        }
        sink.list_();

        endSection();

        for (int j = 0; j < tlds.length; j++)
        {
            doTld(tlds[j]);
            sink.pageBreak();
        }

        sink.body_();
    }

    private String stripTags(String html)
    {
        if (html == null)
        {
            return StringUtils.EMPTY;
        }
        return html.replaceAll("\\<.*?\\>", StringUtils.EMPTY); //$NON-NLS-1$
    }

    /**
     * @param tld Tld
     */
    private void doTld(Tld tld)
    {
        // new section for each tld
        sink.anchor(tld.getFilename());
        sink.anchor_();

        startSection(StringUtils.defaultIfEmpty(tld.getName(), tld.getShortname())
            + " - "
            + MessageFormat.format(Messages.getString("Tagreference.tldversion"), new Object[]{tld.getTlibversion()}));

        sink.paragraph();
        if (parseHtml)
        {
            parseHtml(tld.getInfo());
        }
        else
        {
            sink.text(tld.getInfo());
        }
        sink.paragraph_();

        sink.paragraph();
        sink.bold();
        sink.text("Namespace definition:");
        sink.bold_();
        sink.text(" xmlns:");
        sink.text(tld.getShortname());
        sink.text("=\"");
        sink.text(tld.getUri());
        sink.text("\"");
        sink.paragraph_();

        TldItem[] tags = tld.getTags();
        ELFunction[] functions = tld.getFunctions();
        TagFile[] tagfiles = tld.getTagfiles();

        printList(tld, tags, "Tags");
        printList(tld, functions, "EL Functions");
        printList(tld, tagfiles, "Tagfiles");

        sink.paragraph();
        sink.text(Messages.getString("Tagreference.intro.required")); //$NON-NLS-1$
        sink.bold();
        sink.text(Messages.getString("Tagreference.required.marker")); //$NON-NLS-1$
        sink.bold_();
        sink.paragraph_();

        if (tags != null)
        {
            for (int j = 0; j < tags.length; j++)
            {
                doTag(tld.getShortname(), (Tag) tags[j]);
            }
        }

        if (functions != null)
        {
            for (int j = 0; j < functions.length; j++)
            {
                doFunction(tld.getShortname(), functions[j]);
            }
        }
        if (tagfiles != null)
        {
            for (int j = 0; j < tagfiles.length; j++)
            {
                doTagFile(tld.getShortname(), tagfiles[j]);
            }
        }

        endSection();
    }

    /**
     * @param shortname
     * @param elFunction
     */
    private void doFunction(String prefix, ELFunction tag)
    {

        sink.anchor(prefix + ":" + tag.getName());
        sink.anchor_();

        // new subsection for each tag
        startSection(prefix + ":" + tag.getName() + "(" + tag.getParameters() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        sink.paragraph();
        sink.bold();
        sink.text("Function class: ");
        sink.bold_();
        sink.text(tag.getFunctionClass());
        sink.paragraph_();
        sink.paragraph();
        sink.bold();
        sink.text("Function signature: ");
        sink.bold_();
        sink.text(tag.getFunctionSignature());
        sink.paragraph_();

        if (parseHtml)
        {
            parseHtml(tag.getDescription());
        }
        else
        {
            sink.paragraph();
            sink.text(tag.getDescription());
            sink.paragraph_();
        }

        if (StringUtils.isNotEmpty(tag.getExample()))
        {
            startSection(Messages.getString("Tagreference.example")); //$NON-NLS-1$
            verbatimText(tag.getExample());
            endSection();
        }

        endSection();
    }

    /**
     * @param tld
     * @param tags
     */
    private void printList(Tld tld, TldItem[] tags, String intro)
    {
        if (tags != null && tags.length > 0)
        {

            sink.paragraph();
            sink.bold();
            sink.text(intro);
            sink.bold_();
            sink.paragraph_();

            sink.list();

            for (int j = 0; j < tags.length; j++)
            {
                TldItem tag = tags[j];

                sink.listItem();

                sink.link("#" + tld.getShortname() + ":" + tag.getName()); //$NON-NLS-1$
                if (tag.isDeprecated())
                {
                    sink.italic();
                }
                sink.text(tag.getName());
                if (tag instanceof ELFunction)
                {
                    sink.text("()");
                }

                if (tag.isDeprecated())
                {
                    sink.italic_();
                }
                sink.link_();

                sink.text(" "); //$NON-NLS-1$
                sink.text(stripTags(StringUtils.substringBefore(tag.getDescription(), "."))); //$NON-NLS-1$

                sink.listItem_();
            }

            sink.list_();
        }
    }

    /**
     * Checks a single tag and returns validation results.
     * @param tag Tag
     */
    private void doTag(String prefix, Tag tag)
    {
        sink.anchor(prefix + ":" + tag.getName());
        sink.anchor_();

        // new subsection for each tag
        startSection("<" + prefix + ":" + tag.getName() + ">"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        if (parseHtml)
        {
            parseHtml(tag.getDescription());
        }
        else
        {
            sink.paragraph();
            sink.text(tag.getDescription());
            sink.paragraph_();
        }

        sink.paragraph();
        sink.text(Messages.getString("Tagreference.cancontain")); //$NON-NLS-1$
        sink.text(tag.getBodycontent());
        sink.paragraph_();

        if (StringUtils.isNotEmpty(tag.getExample()))
        {
            startSection(Messages.getString("Tagreference.example")); //$NON-NLS-1$
            verbatimText(tag.getExample());
            endSection();
        }

        // variables

        // attributes
        TagAttribute[] attributes = tag.getAttributes();

        if (attributes != null && attributes.length > 0)
        {
            startSection(Messages.getString("Tagreference.attributes")); //$NON-NLS-1$

            startTable();
            tableHeader(new String[]{
                Messages.getString("Tagreference.attribute.name"), Messages.getString("Tagreference.attribute.description"), Messages.getString("Tagreference.attribute.type")}); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

            for (int i = 0; i < attributes.length; i++)
            {
                TagAttribute attribute = attributes[i];

                sink.tableRow();

                sink.tableCell();
                if (attribute.isDeprecated())
                {
                    sink.italic();
                }
                if (attribute.isRequired())
                {
                    sink.bold();
                }
                sink.text(attribute.getName());
                if (attribute.isRequired())
                {
                    sink.text(Messages.getString("Tagreference.required.marker")); //$NON-NLS-1$
                    sink.bold_();
                }
                if (attribute.isDeprecated())
                {
                    sink.italic_();
                }
                sink.tableCell_();

                sink.tableCell();
                if (attribute.isDeprecated() || StringUtils.isBlank(attribute.getDescription()))
                {
                    sink.italic();
                }

                if (StringUtils.isBlank(attribute.getDescription()))
                {
                    sink.text(Messages.getString("Tagreference.required.marker"));
                }
                else if (parseHtml)
                {
                    parseHtml(attribute.getDescription());
                }
                else
                {
                    sink.text(attribute.getDescription());
                }
                if (attribute.isDeprecated() || StringUtils.isBlank(attribute.getDescription()))
                {
                    sink.italic_();
                }
                sink.tableCell_();

                tableCell(StringUtils.defaultIfEmpty(
                    StringUtils.substringBefore(attribute.getType(), "java.lang."), "String")); //$NON-NLS-1$ //$NON-NLS-2$

                sink.tableRow_();

            }

            endTable();

            endSection();
        }
        else
        {
            paragraph(Messages.getString("Tagreference.noattributes")); //$NON-NLS-1$
        }

        // attributes
        TagVariable[] variables = tag.getVariables();

        if (variables != null && variables.length > 0)
        {
            startSection(Messages.getString("Tagreference.variables")); //$NON-NLS-1$

            startTable();
            tableHeader(new String[]{
                Messages.getString("Tagreference.variable.name"), Messages.getString("Tagreference.variable.type"), Messages.getString("Tagreference.variable.scope"), Messages.getString("Tagreference.variable.description")}); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

            for (int i = 0; i < variables.length; i++)
            {
                TagVariable variable = variables[i];
                sink.tableRow();

                sink.tableCell();
                if (variable.isDeprecated())
                {
                    sink.italic();
                }

                if (variable.getNameGiven() != null)
                {
                    sink.text(variable.getNameGiven());
                    sink.text(Messages.getString("Tagreference.variable.constant")); //$NON-NLS-1$
                }
                else
                {
                    sink.text(Messages.getString("Tagreference.variable.specifiedvia")); //$NON-NLS-1$
                    sink.text(variable.getNameFromAttribute());
                }

                if (variable.isDeprecated())
                {
                    sink.italic_();
                }
                sink.tableCell_();

                tableCell(StringUtils.defaultIfEmpty(StringUtils.substringBefore(variable.getType(), "java.lang."), //$NON-NLS-1$
                    "String")); //$NON-NLS-1$

                tableCell(variable.getScope());

                sink.tableCell();
                if (variable.isDeprecated())
                {
                    sink.italic();
                }
                if (parseHtml)
                {
                    parseHtml(variable.getDescription());
                }
                else
                {
                    sink.text(variable.getDescription());
                }
                if (variable.isDeprecated())
                {
                    sink.italic_();
                }
                sink.tableCell_();

                sink.tableRow_();

            }

            endTable();

            endSection();
        }

        endSection();

    }

    /**
     * Checks a single tag and returns validation results.
     * @param tag Tag
     */
    private void doTagFile(String prefix, TagFile tag)
    {
        sink.anchor(prefix + ":" + tag.getName());
        sink.anchor_();

        // new subsection for each tag
        startSection("<" + prefix + ":" + tag.getName() + ">"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        if (parseHtml)
        {
            parseHtml(tag.getDescription());
        }
        else
        {
            sink.paragraph();
            sink.text(tag.getDescription());
            sink.paragraph_();
        }

        if (StringUtils.isNotEmpty(tag.getExample()))
        {
            startSection(Messages.getString("Tagreference.example")); //$NON-NLS-1$
            verbatimText(tag.getExample());
            endSection();
        }

        endSection();

    }

    private void parseHtml(String description)
    {
        try
        {
            new XhtmlParser().parse(new StringReader(OPEN_DIV + description + CLOSE_DIV), sink);
        }
        catch (ParseException e)
        {
            log.error(description, e);
        }
    }

}