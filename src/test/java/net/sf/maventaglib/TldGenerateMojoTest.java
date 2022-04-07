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
import java.util.Arrays;
import java.util.Properties;

import org.apache.maven.cli.ConsoleDownloadMonitor;
import org.apache.maven.embedder.MavenEmbedderConsoleLogger;
import org.apache.maven.embedder.PlexusLoggerAdapter;
import org.apache.maven.monitor.event.DefaultEventMonitor;
import org.apache.maven.monitor.event.EventMonitor;
import org.apache.maven.project.MavenProject;


/**
 * Test for taglibdoc generation.
 * @author Fabrizio Giustina
 * @version $Revision: 206 $ ($Author: fgiust $)
 */
public class TldGenerateMojoTest extends TaglibPluginTestBase
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
    public final void testTldGenerateWithTags() throws Exception
    {
        File basedir = new File(getTestDirectory(), "project2");
        MavenProject project = maven.readProjectWithDependencies(new File(basedir, "pom.xml"));
        EventMonitor eventMonitor = new DefaultEventMonitor(new PlexusLoggerAdapter(new MavenEmbedderConsoleLogger()));

        this.maven.execute(
            project,
            Arrays.asList(new String[]{"net.sourceforge.maven-taglib:maven-taglib-plugin:tldgenerate" }),
            eventMonitor,
            new ConsoleDownloadMonitor(),
            new Properties(),
            basedir);

        assertFileExists(new File(basedir, "target/classes/META-INF/testtag.tld"));

    }

    public final void testTldGenerateWithFunctions() throws Exception
    {
        File basedir = new File(getTestDirectory(), "project3");
        MavenProject project = maven.readProjectWithDependencies(new File(basedir, "pom.xml"));
        EventMonitor eventMonitor = new DefaultEventMonitor(new PlexusLoggerAdapter(new MavenEmbedderConsoleLogger()));

        this.maven.execute(
            project,
            Arrays.asList(new String[]{"net.sourceforge.maven-taglib:maven-taglib-plugin:tldgenerate" }),
            eventMonitor,
            new ConsoleDownloadMonitor(),
            new Properties(),
            basedir);

        assertFileExists(new File(basedir, "target/classes/META-INF/testtaglib.tld"));

    }

}