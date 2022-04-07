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
import java.net.URL;

import junit.framework.TestCase;

import org.apache.maven.embedder.MavenEmbedder;
import org.apache.maven.embedder.MavenEmbedderConsoleLogger;

/**
 * Base class for plugin tests.
 * @author Fabrizio Giustina
 */
public abstract class TaglibPluginTestBase
    extends TestCase
{

    protected MavenEmbedder maven;

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
        throws Exception
    {

        this.maven = new MavenEmbedder();
        this.maven.setClassLoader( Thread.currentThread().getContextClassLoader() );
        this.maven.setLogger( new MavenEmbedderConsoleLogger() );
        this.maven.start();

        super.setUp();
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown()
        throws Exception
    {
        maven.stop();
        super.tearDown();
    }

    /**
     * returns the path for the test directory.
     * @return path for the test directory
     */
    protected File getTestDirectory()
    {
        URL testFile = TaglibPluginTestBase.class.getResource( "/test-directory.txt" );
        if ( testFile == null )
        {
            fail( "can't find test resources directory" );
        }
        return new File( testFile.getFile() ).getParentFile();
    }

    /**
     * Check that the given file exists and it's not empty.
     * @param file file to be checked
     */
    protected void assertFileExists( File file )
    {
        assertTrue( "Output file [" + file.getAbsolutePath() + "] doesn't exists", file.exists() );
        // assertTrue("Output file [" + file.getAbsolutePath() + "] is empty", file.length() > 0);
    }

}