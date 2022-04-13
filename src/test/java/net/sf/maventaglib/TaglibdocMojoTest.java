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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;


/**
 * Test for taglibdoc generation.
 * @author Fabrizio Giustina
 * @version $Revision: 217 $ ($Author: fgiust $)
 */
public class TaglibdocMojoTest extends TaglibPluginTestBase
{

    /**
     * test for the tag reference goal.
     * @throws Exception any exception thrown during test
     */
    @Test
    public final void testGoalTagreference() throws Exception
    {

        File basedir = new File("target/test-classes/project2/");
        assertNotNull(basedir);
        assertTrue(basedir.exists());

        TaglibdocMojo myMojo = (TaglibdocMojo) rule.lookupConfiguredMojo(basedir, "taglibdoc");
        assertNotNull(myMojo);
        myMojo.execute();

        assertFileExists(new File(basedir, "target/site/tlddoc/project2/ul.html"));
        assertFileExists(new File(basedir, "target/site/tlddoc/project2/url.html"));
    }

    /**
     * test for the tagglibdoc goal.
     * @throws Exception any exception thrown during test
     */
    @Test
    public final void testGoalTaglibDoc() throws Exception
    {
        File basedir = new File("target/test-classes/project1/");
        assertNotNull(basedir);
        assertTrue(basedir.exists());

        TaglibdocMojo myMojo = (TaglibdocMojo) rule.lookupConfiguredMojo(basedir, "taglibdoc");
        assertNotNull(myMojo);
        myMojo.execute();

        assertFileExists(new File(basedir, "target/site/tlddoc/test-12-tld-a/jsp12tldtag1.html"));
    }

}