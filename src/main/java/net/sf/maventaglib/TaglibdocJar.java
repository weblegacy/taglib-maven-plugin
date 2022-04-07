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
