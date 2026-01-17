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

import java.io.File;
import java.io.IOException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.reporting.AbstractMavenReport;
import org.codehaus.plexus.util.FileUtils;

/**
 * An abstract class for the {@code Mojo}s of this plugin.
 *
 * @author ste-gr
 */
public abstract class AbstractReportMojo extends AbstractMavenReport {

    /**
     * If set, only file contained directly in the specified directory are used.
     */
    @Parameter
    protected boolean dontRecurseIntoSubdirs;

    /**
     * Returns {@code true} if at least one file is found with one of the given extensions.
     *
     * @param dir  to source the files
     * @param exts the extensions to test
     *
     * @return {@code true} if at least on file is found
     */
    protected boolean hasFiles(final File dir, final String... exts) {
        if (!dir.isDirectory()) {
            return false;
        }

        final String searchprefix = dontRecurseIntoSubdirs ? "" : "**/";

        try {
            for (String ext : exts) {
                if (!FileUtils.getFiles(dir, searchprefix + "*." + ext, null).isEmpty()) {
                    return true;
                }
            }
        } catch (IOException e) {
            getLog().error(e.getMessage(), e);
        }
        return false;
    }
}
