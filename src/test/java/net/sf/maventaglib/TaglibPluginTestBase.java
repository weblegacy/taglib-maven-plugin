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

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.maven.artifact.repository.MavenArtifactRepository;
import org.apache.maven.artifact.repository.layout.DefaultRepositoryLayout;
import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuilder;
import org.apache.maven.project.ProjectBuildingException;
import org.apache.maven.project.ProjectBuildingRequest;
import org.codehaus.plexus.PlexusTestCase;
import org.codehaus.plexus.component.configurator.ComponentConfigurationException;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.eclipse.aether.DefaultRepositorySystemSession;

/**
 * Base class for plugin tests.
 *
 * @author Fabrizio Giustina
 *
 * @version 2.2
 */
public abstract class TaglibPluginTestBase extends AbstractMojoTestCase {

    /**
     * Executes the {@code goal} of the given {@code project} and returns the {@code path} to this.
     *
     * @param project the project to execute
     * @param goal    the goal of the project to execute
     *
     * @return the path to the project-POM
     *
     * @throws ComponentConfigurationException If the configuration of the component is not success.
     * @throws ComponentLookupException        If the searched component is not found.
     * @throws ProjectBuildingException        If the project descriptor could not be successfully
     *                                         built.
     * @throws MojoExecutionException          if an unexpected problem occurs. Throwing this
     *                                         exception causes a "BUILD ERROR" message to be
     *                                         displayed.
     * @throws MojoFailureException            if an expected problem (such as a compilation
     *                                         failure) occurs. Throwing this exception causes a
     *                                         "BUILD FAILURE" message to be displayed.
     * @throws MalformedURLException           If a protocol handler for the URL could not be found,
     *                                         or if some other error occurred while constructing
     *                                         the URL.
     * @throws Exception                       If any exception is thrown during configuration of
     *                                         the MoJo.
     */
    protected Path mojoExecute(final String project, final String goal) throws
            ComponentConfigurationException, ComponentLookupException, ProjectBuildingException,
            MalformedURLException, MojoExecutionException, MojoFailureException, Exception {

        final Path basedir = Paths.get(PlexusTestCase.getBasedir(), "target", "test-classes",
                project);

        assertNotNull(basedir);

        assertTrue(Files.exists(basedir));

        final Path pom = basedir.resolve("pom.xml");
        final MavenExecutionRequest request = new DefaultMavenExecutionRequest();
        request.setBaseDirectory(basedir.toFile());

        final ProjectBuildingRequest configuration = request.getProjectBuildingRequest();
        configuration.setRepositorySession(new DefaultRepositorySystemSession());

        final MavenProject mavenProject = lookup(ProjectBuilder.class)
                .build(pom.toFile(), configuration)
                .getProject();

        assertNotNull(project);

        final MavenSession session = newMavenSession(mavenProject);

        final Path localRepo = Paths.get(PlexusTestCase.getBasedir(), "target", "local-repo");
        final MavenArtifactRepository repo = new MavenArtifactRepository(
                "localRepository",
                localRepo.toUri().toURL().toString(),
                new DefaultRepositoryLayout(),
                null,
                null);

        session.getRequest().setLocalRepository(repo);

        final Mojo mojo = lookupConfiguredMojo(session, newMojoExecution(goal));
        assertNotNull(mojo);
        mojo.execute();

        return basedir;
    }

    /**
     * Check that the given file exists and it's not empty.
     *
     * @param basedir   base-directory of the given path
     * @param pathParts the path-parts of the given path
     *
     * @throws IOException if an I/O error occurs
     */
    protected static void assertFileExists(final Path basedir, final String... pathParts)
            throws IOException {

        Path path = basedir;
        for (String pathPart : pathParts) {
            path = path.resolve(pathPart);
        }

        assertTrue("Output file [" + path.toAbsolutePath() + "] doesn't exists",
                Files.exists(path));
        assertTrue("Output file [" + path.toAbsolutePath() + "] is empty", Files.size(path) > 0);
    }
}
