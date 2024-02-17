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

import org.apache.maven.artifact.repository.MavenArtifactRepository;
import org.apache.maven.artifact.repository.layout.DefaultRepositoryLayout;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.PlexusTestCase;
import org.junit.Test;


/**
 * Test for tag reference generation.
 * @author Fabrizio Giustina
 * @version $Revision: 217 $ ($Author: fgiust $)
 */
public class TagreferenceMojoTest extends TaglibPluginTestBase
{

    /**
     * test for the tag reference goal.
     * @throws Exception any exception thrown during test
     */
    @Test
    public final void testGoalTagreference() throws Exception
    {
        File basedir = new File("target/test-classes/project1/");
        assertNotNull(basedir);
        assertTrue(basedir.exists());

        MavenProject project = rule.readMavenProject(basedir);
        MavenSession session = rule.newMavenSession(project);

        File localRepo = new File(PlexusTestCase.getBasedir(), "target/local-repo");
        MavenArtifactRepository repo = new MavenArtifactRepository(
                "localRepository",
                "file://" + localRepo.getAbsolutePath(),
                new DefaultRepositoryLayout(),
                null,
                null);

        session.getRequest().setLocalRepository(repo);

        TagreferenceMojo myMojo = (TagreferenceMojo) rule.lookupConfiguredMojo(session, rule.newMojoExecution("tagreference"));
        assertNotNull(myMojo);
        myMojo.execute();

        File outputFile = new File( basedir, "target/site/tagreference.html" );
        assertFileExists( outputFile );
    }

}