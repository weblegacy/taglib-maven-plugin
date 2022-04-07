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

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Fabrizio Giustina
 * @version $Id: Messages.java 206 2010-01-31 09:37:21Z fgiust $
 */
public class Messages
{

    private static final String BUNDLE_NAME = "m2-taglib"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle( BUNDLE_NAME );

    private Messages()
    {
    }

    public static String getString( String key )
    {
        try
        {
            return RESOURCE_BUNDLE.getString( key );
        }
        catch ( MissingResourceException e )
        {
            return '!' + key + '!';
        }
    }
}
