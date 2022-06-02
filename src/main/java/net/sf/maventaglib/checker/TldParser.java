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

import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sf.maventaglib.util.XmlHelper;


/**
 * Reads a tld file and generates a Tld object. This "manual" parser takes in account different versions of the tlds
 * @author Fabrizio Giustina
 * @version $Revision: 217 $ ($Author: fgiust $)
 */
public final class TldParser
{

    /**
     * don't instantiate.
     */
    private TldParser()
    {
        // unused
    }

    /**
     * Parse a Tld object from a document.
     * @param tldDoc Document for the parsed tld
     * @param tldName of the tld file
     * @return Tld instance
     */
    public static Tld parse(Document tldDoc, String tldName)
    {

        Tld tld = new Tld();
        tld.setFilename(tldName);
        Set<Tag> tags = new TreeSet<>();
        Set<ELFunction> functions = new TreeSet<>();
        Set<TagFile> tagfiles = new TreeSet<>();

        NodeList tagList = tldDoc.getElementsByTagName("taglib").item(0).getChildNodes(); //$NON-NLS-1$

        for (int i = 0; i < tagList.getLength(); i++)
        {
            Node tagNode = tagList.item(i);

            if ("shortname".equals(tagNode.getNodeName()) || "short-name".equals(tagNode.getNodeName())) //$NON-NLS-1$ //$NON-NLS-2$
            {
                Node child = tagNode.getFirstChild();
                if (child != null)
                {
                    tld.setShortname(child.getNodeValue());
                }
            }
            else if ("display-name".equals(tagNode.getNodeName())) //$NON-NLS-1$
            {
                Node child = tagNode.getFirstChild();
                if (child != null)
                {
                    tld.setName(child.getNodeValue());
                }
            }
            else if ("info".equals(tagNode.getNodeName()) || "description".equals(tagNode.getNodeName())) //$NON-NLS-1$ //$NON-NLS-2$
            {
                tld.setInfo(XmlHelper.getTextContent(tagNode));
            }
            else if ("tlib-version".equals(tagNode.getNodeName()) || "tlibversion".equals(tagNode.getNodeName())) //$NON-NLS-1$ //$NON-NLS-2$
            {
                Node child = tagNode.getFirstChild();
                if (child != null)
                {
                    tld.setTlibversion(child.getNodeValue());
                }
            }
            else if ("uri".equals(tagNode.getNodeName())) //$NON-NLS-1$
            {
                Node child = tagNode.getFirstChild();
                if (child != null)
                {
                    tld.setUri(child.getNodeValue());
                }
            }
            else if ("tag".equals(tagNode.getNodeName())) //$NON-NLS-1$
            {
                Tag tag = parseTag(tagNode);
                tags.add(tag);
            }
            else if ("function".equals(tagNode.getNodeName())) //$NON-NLS-1$
            {
                ELFunction tag = parseFunction(tagNode);
                functions.add(tag);
            }
            else if ("tag-file".equals(tagNode.getNodeName())) //$NON-NLS-1$
            {
                TagFile tag = parseTagFile(tagNode);
                tagfiles.add(tag);
            }

            tld.setTags(tags.toArray(new Tag[tags.size()]));
            tld.setFunctions(functions.toArray(new ELFunction[functions.size()]));
            tld.setTagfiles(tagfiles.toArray(new TagFile[tagfiles.size()]));
        }

        return tld;
    }

    /**
     * @param tagNode
     * @return
     */
    private static TagFile parseTagFile(Node tagNode)
    {
        TagFile tag = new TagFile();

        NodeList tagAttributes = tagNode.getChildNodes();

        for (int k = 0; k < tagAttributes.getLength(); k++)
        {
            Node tagAttribute = tagAttributes.item(k);
            String nodeName = tagAttribute.getNodeName();

            if ("name".equals(nodeName)) //$NON-NLS-1$
            {
                // tag class name
                tag.setName(tagAttribute.getFirstChild().getNodeValue());
            }
            else if ("description".equals(nodeName)) //$NON-NLS-1$ //$NON-NLS-2$
            {
                tag.setDescription(XmlHelper.getTextContent(tagAttribute));
            }
            else if ("path".equals(nodeName)) //$NON-NLS-1$ //$NON-NLS-2$
            {
                tag.setPath(tagAttribute.getFirstChild().getNodeValue());
            }
            else if ("example".equals(nodeName)) //$NON-NLS-1$
            {
                tag.setExample(XmlHelper.getTextContent(tagAttribute));
            }

        }

        tag.setDeprecated(StringUtils.contains(tag.getDescription(), "@deprecated")); //$NON-NLS-1$

        return tag;
    }

    /**
     * @param tagNode
     * @return
     */
    private static ELFunction parseFunction(Node tagNode)
    {
        ELFunction tag = new ELFunction();
        NodeList tagAttributes = tagNode.getChildNodes();

        for (int k = 0; k < tagAttributes.getLength(); k++)
        {
            Node tagAttribute = tagAttributes.item(k);
            String nodeName = tagAttribute.getNodeName();

            if ("name".equals(nodeName)) //$NON-NLS-1$
            {
                tag.setName(tagAttribute.getFirstChild().getNodeValue());
            }
            else if ("description".equals(nodeName)) //$NON-NLS-1$ //$NON-NLS-2$
            {
                tag.setDescription(XmlHelper.getTextContent(tagAttribute));
            }
            else if ("example".equals(nodeName)) //$NON-NLS-1$
            {
                tag.setExample(XmlHelper.getTextContent(tagAttribute));
            }
            else if ("function-class".equals(nodeName)) //$NON-NLS-1$
            {
                tag.setFunctionClass(StringUtils.trim(XmlHelper.getTextContent(tagAttribute)));
            }
            else if ("function-signature".equals(nodeName)) //$NON-NLS-1$
            {
                String signature = XmlHelper.getTextContent(tagAttribute);
                tag.setFunctionSignature(signature);
                tag.setParameters(StringUtils.substringBetween(signature, "(", ")"));
            }
        }

        tag.setDeprecated(StringUtils.contains(tag.getDescription(), "@deprecated")); //$NON-NLS-1$

        return tag;
    }

    /**
     * Parse a <code>tag</code> element.
     * @param tagNode Node
     * @return a Tag instance
     */
    private static Tag parseTag(Node tagNode)
    {
        Tag tag = new Tag();
        Set<TagAttribute> attributes = new TreeSet<>();
        Set<TagVariable> variables = new TreeSet<>();
        NodeList tagAttributes = tagNode.getChildNodes();

        for (int k = 0; k < tagAttributes.getLength(); k++)
        {
            Node tagAttribute = tagAttributes.item(k);
            String nodeName = tagAttribute.getNodeName();

            if ("name".equals(nodeName)) //$NON-NLS-1$
            {
                // tag class name
                tag.setName(tagAttribute.getFirstChild().getNodeValue());
            }
            else if ("description".equals(nodeName) || "info".equals(nodeName)) //$NON-NLS-1$ //$NON-NLS-2$
            {
                tag.setDescription(XmlHelper.getTextContent(tagAttribute));
            }
            else if ("tag-class".equals(nodeName) || "tagclass".equals(nodeName)) //$NON-NLS-1$ //$NON-NLS-2$
            {
                tag.setTagClass(StringUtils.trim(tagAttribute.getFirstChild().getNodeValue()));
            }
            else if ("body-content".equals(nodeName) || "bodycontent".equals(nodeName)) //$NON-NLS-1$ //$NON-NLS-2$
            {
                tag.setBodycontent(tagAttribute.getFirstChild().getNodeValue());
            }
            else if ("example".equals(nodeName)) //$NON-NLS-1$
            {
                tag.setExample(XmlHelper.getTextContent(tagAttribute));
            }
            else if ("tei-class".equals(nodeName) || "teiclass".equals(nodeName)) //$NON-NLS-1$ //$NON-NLS-2$
            {
                // tei class name
                tag.setTeiClass(StringUtils.trim(tagAttribute.getFirstChild().getNodeValue()));
            }
            else if ("attribute".equals(nodeName)) //$NON-NLS-1$
            {
                TagAttribute attribute = parseTagAttribute(tagAttribute);
                attributes.add(attribute);
            }
            else if ("variable".equals(nodeName)) //$NON-NLS-1$
            {
                TagVariable variable = parseTagVariable(tagAttribute);
                variables.add(variable);
            }
            tag.setAttributes(attributes.toArray(new TagAttribute[attributes.size()]));
            tag.setVariables(variables.toArray(new TagVariable[variables.size()]));
        }

        tag.setDeprecated(StringUtils.contains(tag.getDescription(), "@deprecated")); //$NON-NLS-1$

        return tag;
    }

    /**
     * Parse an <code>attribute</code> element.
     * @param tagAttribute Node
     * @return TagAttribute instance
     */
    private static TagAttribute parseTagAttribute(Node tagAttribute)
    {
        TagAttribute attribute = new TagAttribute();

        NodeList attributeParams = tagAttribute.getChildNodes();
        for (int z = 0; z < attributeParams.getLength(); z++)
        {
            Node param = attributeParams.item(z);
            if (param.getNodeType() != Node.TEXT_NODE && param.hasChildNodes())
            {
                if ("name".equals(param.getNodeName())) //$NON-NLS-1$
                {
                    attribute.setName(param.getFirstChild().getNodeValue());
                }
                else if ("type".equals(param.getNodeName())) //$NON-NLS-1$
                {
                    attribute.setType(StringUtils.trim(param.getFirstChild().getNodeValue()));
                }
                else if ("description".equals(param.getNodeName())) //$NON-NLS-1$
                {
                    attribute.setDescription(XmlHelper.getTextContent(param));
                }
                else if ("required".equals(param.getNodeName())) //$NON-NLS-1$
                {
                    attribute.setRequired(StringUtils.contains(StringUtils.lowerCase(param
                        .getFirstChild()
                        .getNodeValue()), "true")); //$NON-NLS-1$
                }
                else if ("rtexprvalue".equals(param.getNodeName())) //$NON-NLS-1$
                {
                    attribute.setRtexprvalue(StringUtils.contains(StringUtils.lowerCase(param
                        .getFirstChild()
                        .getNodeValue()), "true")); //$NON-NLS-1$
                }
            }
        }

        attribute.setDeprecated(StringUtils.contains(attribute.getDescription(), "@deprecated")); //$NON-NLS-1$

        return attribute;
    }

    /**
     * Parse an <code>attribute</code> element.
     * @param node Node
     * @return TagAttribute instance
     */
    private static TagVariable parseTagVariable(Node node)
    {
        TagVariable variable = new TagVariable();

        NodeList attributeParams = node.getChildNodes();
        for (int z = 0; z < attributeParams.getLength(); z++)
        {
            Node param = attributeParams.item(z);
            if (param.getNodeType() != Node.TEXT_NODE && param.hasChildNodes())
            {
                if ("name-given".equals(param.getNodeName())) //$NON-NLS-1$
                {
                    variable.setNameGiven(param.getFirstChild().getNodeValue());
                }
                if ("name-from-attribute".equals(param.getNodeName())) //$NON-NLS-1$
                {
                    variable.setNameFromAttribute(param.getFirstChild().getNodeValue());
                }
                else if ("variable-class".equals(param.getNodeName())) //$NON-NLS-1$
                {
                    variable.setType(param.getFirstChild().getNodeValue());
                }
                else if ("scope".equals(param.getNodeName())) //$NON-NLS-1$
                {
                    variable.setScope(param.getFirstChild().getNodeValue());
                }
                else if ("description".equals(param.getNodeName()) || "info".equals(param.getNodeName())) //$NON-NLS-1$ //$NON-NLS-2$
                {
                    variable.setDescription(XmlHelper.getTextContent(param));
                }

            }
        }

        variable.setDeprecated(StringUtils.contains(variable.getDescription(), "@deprecated")); //$NON-NLS-1$

        return variable;
    }

}