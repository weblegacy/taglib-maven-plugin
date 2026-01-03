/*
 * The MIT License
 * Copyright © 2004-2014 Fabrizio Giustina
 * Copyright © 2022-2026 Web-Legacy
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

package net.sf.maventaglib.util;

import java.io.CharArrayReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.maven.plugin.MojoExecutionException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Utilities for xml-processing.
 *
 * @author fgiust
 */
public class XmlHelper {

    /**
     * Returns a DocumentBuilder instance.
     *
     * @return DocumentBuilder instance
     *
     * @throws MojoExecutionException in case of an XML-Parser-Error
     */
    public static DocumentBuilder getDocumentBuilder()
            throws MojoExecutionException {
        DocumentBuilder builder;
        DocumentBuilderFactory factory;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setNamespaceAware(false);
            factory.setExpandEntityReferences(false);
            builder.setEntityResolver((publicId, systemId)
                    -> new InputSource(new CharArrayReader(new char[0])));

            return builder;
        } catch (FactoryConfigurationError | ParserConfigurationException e) {
            throw new MojoExecutionException("Unable to obtain a new xml parser", e);
        }
    }

    /**
     * Get Node text content.
     *
     * @param baseNode The node.
     *
     * @return The text content of the node.
     *
     * @throws org.w3c.dom.DOMException Any DOM exceptions.
     */
    public static String getTextContent(Node baseNode) throws DOMException {
        StringBuilder buf = new StringBuilder();

        NodeList nodeList = baseNode.getChildNodes();

        for (int j = 0; j < nodeList.getLength(); j++) {
            Node k = nodeList.item(j);
            buf.append(k.getNodeValue());
            // getTextContent( k );
        }

        return buf.toString();
    }
}
