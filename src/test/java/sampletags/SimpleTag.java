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

package sampletags;

import javax.servlet.jsp.tagext.TagSupport;


/**
 * Simple tag for junit tests.
 * @author Fabrizio Giustina
 * @version $Revision: 206 $ ($Author: fgiust $)
 */
public class SimpleTag extends TagSupport
{

    /**
     * <code>serialVersionUID</code>.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Ant attribute.
     */
    private String ant;

    /**
     * Setter for ant attribute.
     * @return ant attribute value
     */
    public String getAnt()
    {
        return this.ant;
    }

    /**
     * Setter for ant attribute.
     * @param value attribute value
     */
    public void setAnt(String value)
    {
        this.ant = value;
    }

}
