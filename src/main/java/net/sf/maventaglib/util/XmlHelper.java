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

package net.sf.maventaglib.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.CharArrayReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.maven.plugin.MojoExecutionException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * @author fgiust
 * @version $Revision: 206 $ ($Author: fgiust $)
 */
public class XmlHelper
{

    /**
     * Returns a DocumentBuilder instance.
     * @return DocumentBuilder instance
     * @throws FactoryConfigurationError if the parser is not configured
     * @throws ParserConfigurationException if the parser is not configured
     */
    public static DocumentBuilder getDocumentBuilder()
        throws MojoExecutionException
    {
        DocumentBuilder builder;
        try
        {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch ( ParserConfigurationException e )
        {
            throw new MojoExecutionException( "Unable to obtain a new xml parser", e );
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating( false );
        factory.setNamespaceAware( false );
        factory.setExpandEntityReferences( false );
        builder.setEntityResolver( new EntityResolver()
        {

            public InputSource resolveEntity( String publicId, String systemId )
            {
                return new InputSource( new CharArrayReader( new char[0] ) );
            }
        } );

        return builder;
    }

    /**
     * Returns a XMLReader instance.
     * @return XMLReader instance
     * @throws SAXException for errors on building the sax parser
     * @throws ParserConfigurationException if a SAX parser is not configured
     */
    protected static XMLReader getReader()
        throws MojoExecutionException
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware( true );
        factory.setValidating( false );
        XMLReader reader;
        try
        {
            reader = factory.newSAXParser().getXMLReader();
        }
        catch ( Exception e )
        {
            throw new MojoExecutionException( "Unable to obtain a new xml parser", e );
        }

        reader.setEntityResolver( new EntityResolver()
        {

            public InputSource resolveEntity( String publicId, String systemId )
            {
                return new InputSource( new CharArrayReader( new char[0] ) );
            }
        } );
        return reader;
    }

    /**
     * Apply an xsl stylesheet to a java.xml.tranform.Source.
     * @param src Source
     * @param stylesheet xslt used for transformation
     * @param outputFile output file
     * @throws TransformerException
     * @throws Exception xml parsing/transforming exceptions
     */
    protected static void applyXslt( Source src, String stylesheet, File outputFile )
        throws MojoExecutionException, TransformerException
    {
        OutputStream fos = null;
        outputFile.getParentFile().mkdirs();

        try
        {
            try
            {
                fos = new BufferedOutputStream( new FileOutputStream( outputFile ) );
            }
            catch ( FileNotFoundException e )
            {
                throw new MojoExecutionException(
                                                  "Unable to complete xslt transformation. Unable to create output file "
                                                      + outputFile.getAbsolutePath() + ": " + e.getMessage(), e );
            }

            // result
            StreamResult res = new StreamResult( fos );

            // transformer
            InputStream xsl = XmlHelper.class.getResourceAsStream( stylesheet );
            if ( xsl == null )
            {
                throw new MojoExecutionException( "Can't find stylesheet " + stylesheet );
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer( new StreamSource( xsl ) );
            transformer.transform( src, res );

        }
        finally
        {
            try
            {
                if ( fos != null )
                {
                    fos.close();
                }
            }
            catch ( IOException ignored )
            {
                // ignore
            }
        }
    }

    /**
     * Transform a file using the given xsl stylesheet.
     * @param inputFile input file
     * @param stylesheet xslt used for transformation
     * @param outputFile output file
     * @throws Exception xml parsing/transforming exceptions
     */
    protected static void applyXslt( File inputFile, String stylesheet, File outputFile )
        throws MojoExecutionException
    {

        InputStream fis = null;
        try
        {
            fis = new BufferedInputStream( new FileInputStream( inputFile ) );
            XMLReader reader = getReader();
            Source src = new SAXSource( reader, new InputSource( fis ) );
            applyXslt( src, stylesheet, outputFile );
        }
        catch ( Exception e )
        {
            throw new MojoExecutionException( "Unable to complete xslt transformation from "
                + inputFile.getAbsolutePath() + " to " + outputFile.getAbsolutePath() + " using " + stylesheet + ": "
                + e.getMessage(), e );
        }
        finally
        {
            try
            {
                if ( fis != null )
                {
                    fis.close();
                }
            }
            catch ( IOException ignored )
            {
                // ignore
            }
        }
    }

    /**
     * Get Node text content.
     * @param baseNode The node.
     * @return The text content of the node.
     * @throws org.w3c.dom.DOMException Any DOM exceptions.
     */
    public static String getTextContent( Node baseNode )
        throws DOMException
    {

        StringBuffer buf = new StringBuffer();

        NodeList nodeList = baseNode.getChildNodes();

        for ( int j = 0; j < nodeList.getLength(); j++ )
        {
            Node k = nodeList.item( j );
            buf.append( k.getNodeValue() );
            getTextContent( k );
        }

        return buf.toString();
    }

}
