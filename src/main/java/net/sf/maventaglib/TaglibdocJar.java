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
import java.io.IOException;

import org.apache.maven.artifact.handler.ArtifactHandler;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.archiver.ArchiverException;
import org.codehaus.plexus.archiver.jar.JarArchiver;

/**
 * Generates a jar containing the tlddoc generated documentation. The generated jar is installed/deployed to the
 * repository if install/deploy is executed after this goal.
 * @goal taglibdocjar
 * @phase package
 * @execute goal="taglibdoc"
 */
public class TaglibdocJar
    extends AbstractMojo
{

    /**
     * TldDoc output dir.
     * @parameter expression="${project.reporting.outputDirectory}/tlddoc"
     */
    private File tldDocDir;

    /**
     * Maven project.
     * @parameter expression="${project}"
     * @readonly
     * @required
     */
    private MavenProject project;

    /**
     * Maven Project Helper.
     * @component
     */
    private MavenProjectHelper projectHelper;

    /**
     * Generated jar name/location.
     * @parameter expression="${project.build.directory}/${project.build.finalName}-tlddoc.jar"
     * @required
     */
    private File tlddocJar;

    /**
     * Attach the generated jar to the main artifact. Set this to false if you don't want the taglibdoc jar deployed to
     * the repository.
     * @parameter expression="${attach}" default-value="true"
     */
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
        catch ( ArchiverException e )
        {
            throw new MojoExecutionException( "Error while creating archive.", e );
        }
        catch ( IOException e )
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

        if ( tlddocJar.exists() )
        {
            tlddocJar.delete();
        }

        JarArchiver archiver = new JarArchiver();

        archiver.addDirectory( tdldocDir );
        archiver.setDestFile( tlddocJar );
        archiver.createArchive();

        return tlddocJar;
    }

}
