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
