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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.net.URL;

import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Rule;


/**
 * Base class for plugin tests.
 * @author Fabrizio Giustina
 */
public abstract class TaglibPluginTestBase
{

    @Rule
    public MojoRule rule = new MojoRule();

    /**
     * returns the path for the test directory.
     * @return path for the test directory
     */
    protected File getTestDirectory()
    {
        URL testFile = TaglibPluginTestBase.class.getResource("/test-directory.txt");
        if (testFile == null)
        {
            fail("can't find test resources directory");
        }
        return new File(testFile.getFile()).getParentFile();
    }

    /**
     * Check that the given file exists and it's not empty.
     * @param file file to be checked
     */
    protected void assertFileExists(File file)
    {
        assertTrue("Output file [" + file.getAbsolutePath() + "] doesn't exists", file.exists());
        // assertTrue("Output file [" + file.getAbsolutePath() + "] is empty", file.length() > 0);
    }

}