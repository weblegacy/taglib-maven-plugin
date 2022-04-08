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


/**
 * Test for taglibdoc generation.
 * @author Fabrizio Giustina
 * @version $Revision: 217 $ ($Author: fgiust $)
 */
public class TaglibdocMojoTest extends TaglibPluginTestBase
{

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
    }

    /**
     * test for the tag reference goal.
     * @throws Exception any exception thrown during test
     */
    public final void testGoalTagreference() throws Exception
    {

        File pom = getTestFile("src/test/resources/project2/pom.xml");
        assertNotNull(pom);
        assertTrue(pom.exists());

        TaglibdocMojo myMojo = (TaglibdocMojo) lookupMojo("taglib:taglibdoc", pom);
        assertNotNull(myMojo);
        myMojo.execute();

        assertFileExists(new File(getBasedir(), "target/site/tlddoc/project2/ul.html"));
        assertFileExists(new File(getBasedir(), "target/site/tlddoc/project2/url.html"));
    }

    /**
     * test for the tagglibdoc goal.
     * @throws Exception any exception thrown during test
     */
    public final void testGoalTaglibDoc() throws Exception
    {
        File pom = getTestFile("src/test/resources/project1/pom.xml");
        assertNotNull(pom);
        assertTrue(pom.exists());

        TaglibdocMojo myMojo = (TaglibdocMojo) lookupMojo("taglib:taglibdoc", pom);
        assertNotNull(myMojo);
        myMojo.execute();

        assertFileExists(new File(getBasedir(), "target/site/tlddoc/test-12-tld-a/jsp12tldtag1.html"));
    }

}