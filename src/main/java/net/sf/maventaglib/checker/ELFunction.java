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
 * Contains information about an EL function.
 * @author Fabrizio Giustina
 * @version $Revision $ ($Author $)
 */
public class ELFunction extends TldItem
{

    private String functionClass;

    private String functionSignature;

    private String parameters;

    /**
     * Returns the functionClass.
     * @return the functionClass
     */
    public String getFunctionClass()
    {
        return functionClass;
    }

    /**
     * Sets the functionClass.
     * @param functionClass the functionClass to set
     */
    public void setFunctionClass(String functionClass)
    {
        this.functionClass = functionClass;
    }

    /**
     * Returns the functionSignature.
     * @return the functionSignature
     */
    public String getFunctionSignature()
    {
        return functionSignature;
    }

    /**
     * Sets the functionSignature.
     * @param functionSignature the functionSignature to set
     */
    public void setFunctionSignature(String functionSignature)
    {
        this.functionSignature = functionSignature;
    }

    /**
     * Returns the parameters.
     * @return the parameters
     */
    public String getParameters()
    {
        return parameters;
    }

    /**
     * Sets the parameters.
     * @param parameters the parameters to set
     */
    public void setParameters(String parameters)
    {
        this.parameters = parameters;
    }

}
