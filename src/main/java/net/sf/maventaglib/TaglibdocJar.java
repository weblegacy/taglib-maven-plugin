/*
 * The MIT License
 * Copyright © 2004-2014 Fabrizio Giustina
 * Copyright © 2022-2024 Web-Legacy
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
import java.io.IOException;
import java.nio.file.Files;

import org.apache.maven.artifact.handler.ArtifactHandler;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.archiver.ArchiverException;
import org.codehaus.plexus.archiver.jar.JarArchiver;

/**
 * Generates a jar containing the tlddoc generated documentation. The generated jar is installed/deployed to the
 * repository if install/deploy is executed after this goal.
 */
@Mojo(name="taglibdocjar", defaultPhase=LifecyclePhase.PACKAGE)
@Execute(goal="taglibdoc")
public class TaglibdocJar
    extends AbstractMojo
{

    /**
     * TldDoc output dir.
     */
    @Parameter(defaultValue="${project.reporting.outputDirectory}/tlddoc")
    private File tldDocDir;

    /**
     * Maven project.
     */
    @Parameter(property="project", readonly=true, required=true)
    private MavenProject project;

    /**
     * Maven Project Helper.
     */
    @Component
    private MavenProjectHelper projectHelper;

    /**
     * Generated jar name/location.
     */
    @Parameter(defaultValue="${project.build.directory}/${project.build.finalName}-tlddoc.jar", required=true)
    private File tlddocJar;

    /**
     * Attach the generated jar to the main artifact. Set this to false if you don't want the taglibdoc jar deployed to
     * the repository.
     */
    @Parameter(property="attach", defaultValue="true")
    private boolean attach = true;

    public void execute()
        throws MojoExecutionException
    {
        ArtifactHandler artifactHandler = project.getArtifact().getArtifactHandler();
        if ( !"java".equals( artifactHandler.getLanguage() ) )
        {
            getLog().info( "Not executing tlddoc as the project is not a Java classpath-capable package" );
            return;
        }

        try
        {
            File outputFile = generateArchive( tldDocDir, tlddocJar );

            if ( !attach )
            {
                getLog().info( "NOT adding tlddoc to attached artifacts list." );
            }
            else
            {
                projectHelper.attachArtifact( project, "jar", "tlddoc", outputFile );
            }
        }
        catch ( ArchiverException | IOException e )
        {
            throw new MojoExecutionException( "Error while creating archive.", e );
        }
    }

    private File generateArchive( File tdldocDir, File tlddocJar )
        throws MojoExecutionException, ArchiverException, IOException
    {

        if ( !tdldocDir.exists() )
        {
            throw new MojoExecutionException( "tlddoc files not found." );
        }

        if ( !Files.deleteIfExists(tlddocJar.toPath()) )
        {
            throw new MojoExecutionException( "tlddocJar could not deleted." );
        }

        JarArchiver archiver = new JarArchiver();

        archiver.addDirectory( tdldocDir );
        archiver.setDestFile( tlddocJar );
        archiver.createArchive();

        return tlddocJar;
    }

}