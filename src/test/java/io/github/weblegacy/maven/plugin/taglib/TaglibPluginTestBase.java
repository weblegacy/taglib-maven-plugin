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

package io.github.weblegacy.maven.plugin.taglib;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.inject.Inject;
import org.apache.maven.api.plugin.testing.MojoExtension;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for plugin tests.
 *
 * @author Fabrizio Giustina
 *
 * @version 2.2
 */
public abstract class TaglibPluginTestBase {

    /**
     * Injected Maven-Session.
     */
    @Inject
    protected MavenSession session;

    /**
     * Base directory where all test-project are.
     */
    protected static final String TEST_DIR = "target/test-classes/";

    /**
     * Set local repository path for each test.
     */
    @BeforeEach
    void beforeEach() {
        session.getRequest().setLocalRepositoryPath(
                Paths.get("target", "local-repo").toAbsolutePath().toFile());
    }

    /**
     * Executes a {@code mojo}. In the case of a {@code report mojo}, the output directory is set
     * according to the goal name and RepositorySystemSession is initialized.
     *
     * @param mojo Mojo to execute
     *
     * @throws MojoExecutionException if an unexpected problem occurs.
     *                 Throwing this exception causes a "BUILD ERROR" message to be displayed.
     * @throws MojoFailureException   if an expected problem (such as a compilation failure) occurs.
     *                 Throwing this exception causes a "BUILD FAILURE" message to be displayed.
     * @throws IllegalAccessException if the field {@code repoSession} is enforcing Java language
     *                 access control and the underlying field is either inaccessible or final.
     */
    protected void execute(AbstractMojo mojo) throws MojoExecutionException, MojoFailureException,
            IllegalAccessException {

        if (mojo instanceof AbstractReportMojo) {
            MojoExecution execution = MojoExtension.getVariableValueFromObject(mojo,
                    "mojoExecution");

            AbstractReportMojo reportMojo = (AbstractReportMojo) mojo;
            reportMojo.setReportOutputDirectory(
                    MojoExtension.getTestFile(execution.getMojoDescriptor().getGoal()));

            MojoExtension.setVariableValueToObject(mojo, "repoSession",
                    session.getRepositorySession());
        }

        mojo.execute();
    }

    /**
     * Check that the given file exists and it's not empty.
     *
     * @param pathParts the path-parts of the given path
     *
     * @throws IOException if an I/O error occurs
     */
    protected static void assertFileExists(final String... pathParts)
            throws IOException {

        Path path = Paths.get(MojoExtension.getBasedir());
        for (String pathPart : pathParts) {
            path = path.resolve(pathPart);
        }

        assertTrue(Files.exists(path),
                "Output file [" + path.toAbsolutePath() + "] doesn't exists");
        assertTrue(Files.size(path) > 0, "Output file [" + path.toAbsolutePath() + "] is empty");
    }
}
