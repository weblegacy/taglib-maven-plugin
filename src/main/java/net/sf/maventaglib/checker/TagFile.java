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

package net.sf.maventaglib.checker;



/**
 * @author fgiust
 * @version $Id: TagFile.java 206 2010-01-31 09:37:21Z fgiust $
 */
public class TagFile extends TldItem
{

    private String path;

    /**
     * Returns the path.
     * @return the path
     */
    public String getPath()
    {
        return path;
    }

    /**
     * Sets the path.
     * @param path the path to set
     */
    public void setPath(String path)
    {
        this.path = path;
    }

}
