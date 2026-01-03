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

package net.sf.maventaglib;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.sf.maventaglib.checker.ElFunction;
import net.sf.maventaglib.checker.Tag;
import net.sf.maventaglib.checker.TagAttribute;
import net.sf.maventaglib.checker.Tld;
import net.sf.maventaglib.util.JspCheck;
import net.sf.maventaglib.util.JspClass;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.reporting.AbstractMavenReportRenderer;

/**
 * Validates tag handler classes fount in tlds.
 *
 * @author Fabrizio Giustina
 */
public class ValidateRenderer extends AbstractMavenTaglibReportRenderer {

    /**
     * Code for Success-Icon.
     */
    private static final int ICO_SUCCESS = 0;

    /**
     * Code for Info-Icon.
     */
    private static final int ICO_INFO = 1;

    /**
     * Code for Warning-Icon.
     */
    private static final int ICO_WARNING = 2;

    /**
     * Code for Error-Icon.
     */
    private static final int ICO_ERROR = 3;

    /**
     * Path to Error-Icon.
     */
    private static final String IMAGE_ERROR_SRC = Messages.getString("Validate.image.error");

    /**
     * Path to Waring-Icon.
     */
    private static final String IMAGE_WARNING_SRC = Messages.getString("Validate.image.warning");

    /**
     * Path to Info-Icon.
     */
    private static final String IMAGE_INFO_SRC = Messages.getString("Validate.image.info");

    /**
     * Path to Success-Icon.
     */
    private static final String IMAGE_SUCCESS_SRC = Messages.getString("Validate.image.success");

    /**
     * list of Tld to check.
     */
    private final Tld[] tlds;

    /**
     * For logging.
     */
    private final Log log;

    /**
     * The class-loader for the project.
     */
    private final ClassLoader projectClassLoader;

    /**
     * Utility to check for the loaded Jsp-Classes.
     */
    private final JspCheck jspCheck;

    /**
     * The class-constructor.
     *
     * @param sink               the sink to use.
     * @param locale             the wanted locale to return the report's description, could be
     *                           <code>null</code>.
     * @param tlds               list of TLDs to check.
     * @param log                the logger that has been injected into this mojo.
     * @param projectClassLoader ClassLoader for all compile-classpaths
     */
    public ValidateRenderer(final Sink sink, final Locale locale, final Tld[] tlds, final Log log,
            final ClassLoader projectClassLoader) {

        super(sink, locale);
        this.tlds = tlds;
        this.log = log;
        this.projectClassLoader = projectClassLoader;

        // Load all jsp-classes for all namespaces.
        this.jspCheck = new JspCheck(log, projectClassLoader);
    }

    @Override
    public String getTitle() {
        return getMessageString("Validate.title");
    }

    /**
     * Check the given tld. Assure that:
     * <ul>
     * <li>Any tag class is loadable</li>
     * <li>the tag class has a setter for any of the declared attribute</li>
     * <li>the type declared in the dtd for an attribute (if any) matches the type accepted by the
     * getter</li>
     * </ul>
     *
     * @see AbstractMavenReportRenderer#renderBody()
     */
    @Override
    protected void renderBody() {
        sink.body();
        startSection(getMessageString("Validate.h1"));
        paragraph(getMessageString("Validate.into1"));
        paragraph(getMessageString("Validate.intro2"));

        sink.list();
        for (Tld tld : tlds) {

            sink.listItem();
            sink.link("#" + tld.getFilename());
            sink.text(MessageFormat.format(getMessageString("Validate.listitem.tld"),
                    StringUtils.defaultIfEmpty(tld.getName(), tld.getShortname()),
                    tld.getFilename()));
            sink.link_();
            sink.text(getMessageString("Validate.listitem.uri") + tld.getUri());

            sink.listItem_();
        }
        sink.list_();

        endSection();

        for (Tld tld : tlds) {
            checkTld(tld);
        }

        sink.body_();
    }

    /**
     * Checks a single tld and returns validation results.
     *
     * @param tld Tld
     */
    private void checkTld(Tld tld) {
        // new section for each tld
        sink.anchor(tld.getFilename());
        sink.anchor_();
        startSection(StringUtils.defaultIfEmpty(tld.getName(),
                tld.getShortname()) + ' ' + tld.getFilename());

        doTags(tld.getTags(), tld.getShortname());
        doFunctions(tld.getFunctions(), tld.getShortname());

        endSection();
    }

    /**
     * Checks the tags and returns validation results.
     *
     * @param tags      tags of the Tld
     * @param shortname shortname of the Tld
     */
    private void doTags(Tag[] tags, String shortname) {
        if (tags != null && tags.length > 0) {
            for (Tag tldItem : tags) {
                checkTag(shortname, tldItem);
            }
        }
    }

    /**
     * Checks the functions and returns validation results.
     *
     * @param tags      functions of the Tld
     * @param shortname shortname of the Tld
     */
    private void doFunctions(ElFunction[] tags, String shortname) {
        if (tags != null && tags.length > 0) {

            startSection("EL functions");

            startTable();

            tableHeader(new String[]{
                getMessageString("Validate.header.validated"),
                "function",
                getMessageString("Validate.header.class"),
                getMessageString("Validate.header.signature")
            });

            for (ElFunction tldItem : tags) {
                checkFunction(shortname, tldItem);
            }

            endTable();

            endSection();
        }
    }

    /**
     * Checks a function and returns the validation result.
     *
     * @param prefix prefix of the function
     * @param tag    the function
     */
    private void checkFunction(String prefix, ElFunction tag) {
        String className = tag.getFunctionClass();

        boolean found = true;

        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(this.projectClassLoader);

        try {
            Class<?> functionClass = Class.forName(className, true, this.projectClassLoader);

            String fullSignature = tag.getFunctionSignature();
            String paramsString = tag.getParameters();
            String returnvalue = null;

            String methodName = StringUtils.trim(StringUtils.substringBefore(fullSignature, "("));
            if (StringUtils.contains(methodName, " ")) {
                returnvalue = StringUtils.substringBefore(methodName, " ");
                methodName = StringUtils.substringAfter(methodName, " ");
            }

            String[] params = StringUtils.split(paramsString, ",");

            List<Class<?>> parClasses = new ArrayList<>(params.length);

            for (String stringClass : params) {
                parClasses.add(Class.forName(StringUtils.trim(stringClass), true,
                        this.projectClassLoader));
            }

            Method method = functionClass.getMethod(methodName,
                    parClasses.toArray(Class<?>[]::new));

            Class<?> returnType = method.getReturnType();

            if (!(returnvalue == null || returnType.getCanonicalName().equals(returnvalue))) {
                found = false;
            }
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException e) {
            found = false;
        }

        Thread.currentThread().setContextClassLoader(currentClassLoader);

        sink.tableRow();

        sink.tableCell();
        figure(found ? ICO_SUCCESS : ICO_ERROR);
        sink.tableCell_();

        tableCell(prefix + ":" + tag.getName() + "()");
        tableCell(className);
        tableCell(tag.getFunctionSignature());

        sink.tableRow_();
    }

    /**
     * Checks a single tag and returns validation results.
     *
     * @param prefix prefix of the tag
     * @param tag    Tag
     */
    private void checkTag(String prefix, Tag tag) {

        // new subsection for each tag
        startSection("<" + prefix + ":" + tag.getName() + ">");

        String className = tag.getTagClass();

        startTable();

        tableHeader(new String[]{
            getMessageString("Validate.header.found"),
            getMessageString("Validate.header.loadable"),
            getMessageString("Validate.header.extends"),
            getMessageString("Validate.header.class")
        });

        boolean found = true;
        boolean loadable = true;
        boolean extend;

        Object tagObject = null;
        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(this.projectClassLoader);

        try {
            Class<?> tagClass = Class.forName(className, true, this.projectClassLoader);

            // extend only true, if tagClass derives from TagSupport or derives from SimpleTag
            extend = jspCheck.check(JspClass.TAG_SUPPORT, tagClass)
                    || jspCheck.check(JspClass.SIMPLE_TAG, tagClass);

            try {
                tagObject = tagClass.getDeclaredConstructor().newInstance();
            } catch (IllegalAccessException | IllegalArgumentException | InstantiationException
                    | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                loadable = false;
            }
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
            found = false;
            loadable = false;
            extend = false;
        }

        Thread.currentThread().setContextClassLoader(currentClassLoader);

        sink.tableRow();

        sink.tableCell();
        figure(found ? ICO_SUCCESS : ICO_ERROR);
        sink.tableCell_();

        sink.tableCell();
        figure(loadable ? ICO_SUCCESS : ICO_ERROR);
        sink.tableCell_();

        sink.tableCell();
        figure(extend ? ICO_SUCCESS : ICO_ERROR);
        sink.tableCell_();

        tableCell(className);

        sink.tableRow_();

        if (tag.getTeiClass() != null) {
            checkTeiClass(tag.getTeiClass());
        }

        endTable();

        TagAttribute[] attributes = tag.getAttributes();
        if (tagObject != null && attributes.length > 0) {

            startTable();
            tableHeader(new String[]{
                StringUtils.EMPTY,
                getMessageString("Validate.header.attributename"),
                getMessageString("Validate.header.tlddeclares"),
                getMessageString("Validate.header.tagdeclares")
            });

            for (TagAttribute attribute : attributes) {
                checkAttribute(tagObject, attribute);
            }

            endTable();
        }
        endSection();
    }

    /**
     * Check a declared TagExtraInfo class.
     *
     * @param className TEI class name
     */
    private void checkTeiClass(String className) {

        boolean found = true;
        boolean loadable = true;
        boolean extend;

        Class<?> teiClass;
        try {
            teiClass = Class.forName(className, true, this.projectClassLoader);

            extend = jspCheck.check(JspClass.TAG_EXTRA_INFO, teiClass);

            try {
                teiClass.getDeclaredConstructor().newInstance();
            } catch (IllegalAccessException | IllegalArgumentException | InstantiationException
                    | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                loadable = false;
            }
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
            found = false;
            loadable = false;
            extend = false;
        }

        sink.tableRow();

        sink.tableCell();
        figure(found ? ICO_SUCCESS : ICO_ERROR);
        sink.tableCell_();

        sink.tableCell();
        figure(loadable ? ICO_SUCCESS : ICO_ERROR);
        sink.tableCell_();

        sink.tableCell();
        figure(extend ? ICO_SUCCESS : ICO_ERROR);
        sink.tableCell_();

        sink.tableCell();
        sink.text(className);
        sink.tableCell_();

        sink.tableRow_();
    }

    /**
     * Checks a single attribute and returns validation results.
     *
     * @param tag       tag handler instance
     * @param attribute TagAttribute
     */
    private void checkAttribute(Object tag, TagAttribute attribute) {

        String tldType = attribute.getType();
        String tldName = attribute.getName();
        Class<?> tagType = null;
        String tagTypeName = null;

        List<ValidationError> validationErrors = new ArrayList<>(3);

        if (!PropertyUtils.isWriteable(tag, tldName)) {
            validationErrors.add(new ValidationError(ValidationError.LEVEL_ERROR,
                    getMessageString("Validate.error.setternotfound")));
        }

        // don't check if setter is missing
        if (validationErrors.isEmpty()) {

            try {
                tagType = PropertyUtils.getPropertyType(tag, tldName);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                // should never happen, since we already checked the writable property
                log.warn(e);
            }
            tagTypeName = tagType == null ? StringUtils.EMPTY : tagType.getName();

            if (tldType != null && tagType != null) {
                Class<?> tldTypeClass = getClassFromName(tldType);

                if (!tagType.isAssignableFrom(tldTypeClass)) {

                    validationErrors.add(new ValidationError(ValidationError.LEVEL_ERROR,
                            MessageFormat.format(
                                    getMessageString("Validate.error.attributetypemismatch"),
                                    tldType, tagType.getName())));
                }
            }
        }

        // don't check if we already know type is different
        if (validationErrors.isEmpty()) {

            if (tldType != null && tagType != null && !tldType.equals(tagType.getName())) {
                validationErrors.add(new ValidationError(ValidationError.LEVEL_WARNING,
                        MessageFormat.format(
                                getMessageString("Validate.error.attributetypeinexactmatch"),
                                tldType, tagType.getName())));
            } else if (tldType == null && !String.class.equals(tagType)) {
                validationErrors.add(new ValidationError(ValidationError.LEVEL_INFO,
                        getMessageString("Validate.error.attributetype")));
            }
        }

        sink.tableRow();

        sink.tableCell();

        boolean errors = false;
        boolean warnings = false;
        boolean infos = false;
        for (ValidationError error : validationErrors) {
            switch (error.getLevel()) {
                case ValidationError.LEVEL_ERROR:
                    errors = true;
                    break;
                case ValidationError.LEVEL_WARNING:
                    warnings = true;
                    break;
                case ValidationError.LEVEL_INFO:
                    infos = true;
                    break;
                default:
                    break;
            }
        }

        final int figure;
        if (errors) {
            figure = ICO_ERROR;
        } else if (warnings) {
            figure = ICO_WARNING;
        } else if (infos) {
            figure = ICO_INFO;
        } else {
            figure = ICO_SUCCESS;
        }

        figure(figure);
        sink.tableCell_();

        sink.tableCell();
        sink.text(tldName);

        for (ValidationError error : validationErrors) {
            sink.lineBreak();
            if (error.getLevel() == ValidationError.LEVEL_ERROR) {
                sink.bold();
            }
            sink.text(error.getText());
            if (error.getLevel() == ValidationError.LEVEL_ERROR) {
                sink.bold_();

            }
        }

        sink.tableCell_();

        sink.tableCell();
        if (tldType != null) {
            sink.text(StringUtils.substringAfter(tldType, "java.lang."));
        }
        sink.tableCell_();

        tableCell(StringUtils.substringAfter(tagTypeName, "java.lang."));

        sink.tableRow_();

    }

    private void figure(int type) {
        String text;
        String src;

        switch (type) {
            case ICO_ERROR:
                text = getMessageString("Validate.level.error");
                src = IMAGE_ERROR_SRC;
                break;
            case ICO_WARNING:
                text = getMessageString("Validate.level.warning");
                src = IMAGE_WARNING_SRC;
                break;
            case ICO_INFO:
                text = getMessageString("Validate.level.info");
                src = IMAGE_INFO_SRC;
                break;
            default:
                text = getMessageString("Validate.level.success");
                src = IMAGE_SUCCESS_SRC;
                break;
        }

        sink.figure();
        sink.figureGraphics(src);
        sink.figureCaption();
        sink.text(text);
        sink.figureCaption_();
        sink.figure_();
    }

    /**
     * returns a class from its name, handling primitives.
     *
     * @param className clss name
     *
     * @return Class istantiated using Class.forName or the matching primitive.
     */
    private Class<?> getClassFromName(String className) {

        Class<?> tldTypeClass = tryGettingPrimitiveClass(className);

        if (tldTypeClass == null) {
            // not a primitive type
            try {
                if (isArrayClassName(className)) {
                    tldTypeClass = getArrayClass(className);
                } else {
                    tldTypeClass = Class.forName(className, true, this.projectClassLoader);
                }
            } catch (ClassNotFoundException e) {
                log.error(MessageFormat.format(
                        Messages.getString("Validate.error.unabletofindclass"), className));
            }
        }
        return tldTypeClass;
    }

    private Class<?> tryGettingPrimitiveClass(String className) {
        if (className == null) {
            return null;
        }

        switch (className) {
            case "byte":
                return byte.class;
            case "short":
                return int.class;
            case "int":
                return int.class;
            case "long":
                return long.class;
            case "float":
                return double.class;
            case "double":
                return double.class;
            case "boolean":
                return boolean.class;
            case "char":
                return char.class;
            default:
                return null;
        }
    }

    /**
     * Tests if the given {@code className} as an array.
     *
     * @param className the className to test
     *
     * @return {@code true} if the given {@code className} as an array
     */
    private boolean isArrayClassName(String className) {
        return className.endsWith("[]");
    }

    /**
     * Gets the class of an array with the elements of {@code className}.
     *
     * @param className elements-class of the array
     *
     * @return the array-class
     *
     * @throws ClassNotFoundException if the class is not found
     */
    private Class<?> getArrayClass(String className) throws ClassNotFoundException {
        String elementClassName = StringUtils.replace(className, "[]", "");
        Class<?> elementClass = tryGettingPrimitiveClass(elementClassName);
        if (elementClass == null) {
            elementClass = Class.forName(elementClassName);
        }
        return Array.newInstance(elementClass, 0).getClass();
    }

    static class ValidationError {

        /**
         * Level of validation is information.
         */
        public static final int LEVEL_INFO = 1;

        /**
         * Level of validation is warning.
         */
        public static final int LEVEL_WARNING = 2;

        /**
         * Level of validation is error.
         */
        public static final int LEVEL_ERROR = 3;

        /**
         * The level of the validation.
         */
        private int level;

        /**
         * The text of the validation.
         */
        private String text;

        /**
         * The class-constructor.
         *
         * @param level the level of the validation
         * @param text  the text of the validation
         */
        public ValidationError(int level, String text) {
            this.level = level;
            this.text = text;
        }

        /**
         * Getter for {@code level}.
         *
         * @return Returns the level.
         */
        public int getLevel() {
            return this.level;
        }

        /**
         * Setter for {@code level}.
         *
         * @param level The level to set.
         */
        public void setLevel(int level) {
            this.level = level;
        }

        /**
         * Getter for {@code text}.
         *
         * @return Returns the text.
         */
        public String getText() {
            return this.text;
        }

        /**
         * Setter for {@code text}.
         *
         * @param text The text to set.
         */
        public void setText(String text) {
            this.text = text;
        }
    }
}
