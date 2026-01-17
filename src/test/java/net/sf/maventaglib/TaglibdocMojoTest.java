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

package net.sf.maventaglib;

import org.apache.maven.api.plugin.testing.Basedir;
import org.apache.maven.api.plugin.testing.InjectMojo;
import org.apache.maven.api.plugin.testing.MojoParameter;
import org.apache.maven.api.plugin.testing.MojoTest;
import org.junit.jupiter.api.Test;

/**
 * Test for taglibdoc generation.
 *
 * @author Fabrizio Giustina
 *
 * @version 2.3
 */
@MojoTest(realRepositorySession = true)
public class TaglibdocMojoTest extends TaglibPluginTestBase {

    /**
     * Test for the taglibdoc goal (tag-files).
     *
     * @throws Exception any exception thrown during test
     */
    @Test
    @Basedir(TEST_DIR + "project2")
    @InjectMojo(goal = "taglibdoc")
    @MojoParameter(name = "tldDocDir", value = "taglibdoc")
    public void testGoalTaglibDocWithTags(TaglibdocMojo mojo) throws Exception {
        execute(mojo);

        assertFileExists("taglibdoc", "project2", "ul.html");
        assertFileExists("taglibdoc", "project2", "url.html");
    }

    /**
     * Test for the taglibdoc goal (tld-file).
     *
     * @throws Exception any exception thrown during test
     */
    @Test
    @Basedir(TEST_DIR + "project1")
    @InjectMojo(goal = "taglibdoc")
    @MojoParameter(name = "tldDocDir", value = "taglibdoc")
    public void testGoalTaglibDoc(TaglibdocMojo mojo) throws Exception {
        execute(mojo);

        assertFileExists("taglibdoc", "test-12-tld-a", "jsp12tldtag1.html");
    }
}
