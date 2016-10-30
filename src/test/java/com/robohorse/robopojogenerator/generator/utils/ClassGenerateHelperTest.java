package com.robohorse.robopojogenerator.generator.utils;

import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.generator.common.ClassDecorator;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by vadim on 28.09.16.
 */
public class ClassGenerateHelperTest {
    private ClassGenerateHelper classGenerateHelper = new ClassGenerateHelper();

    @Test
    public void getClassNameModification_isCorrect() throws Exception {
        assertEquals("Item", classGenerateHelper.formatClassName("item"));
    }

    @Test
    public void testModification_isCorrect() throws Exception {
        Exception exception = null;
        try {
            classGenerateHelper.validateJsonContent("{\"data\":1}");
        } catch (RoboPluginException e) {
            exception = e;
        }
        assertNull(exception);
    }

    @Test
    public void testModificationWithWrongStructure_isCorrect() throws Exception {
        Exception exception = null;
        try {
            classGenerateHelper.validateJsonContent("1123");
        } catch (RoboPluginException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @Test
    public void testValidateClassName_isCorrect() throws Exception {
        Exception exception = null;
        try {
            classGenerateHelper.validateClassName("MySuperAwesomeClass");
        } catch (RoboPluginException e) {
            exception = e;
        }
        assertNull(exception);
    }

    @Test
    public void testValidateClassNameWithWrongName_isCorrect() throws Exception {
        Exception exception = null;
        try {
            classGenerateHelper.validateClassName(".MySuperAwesomeClass23");
        } catch (RoboPluginException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @Test
    public void testResolveMajorTypeWithSingleCount_isCorrect() throws Exception {
        final String type = "Double";
        ClassDecorator classDecorator = new ClassDecorator();
        classDecorator.setClassDecorator(new ClassDecorator(type));
        assertEquals("List<" + type + ">", classDecorator.getJavaItem());
    }

    @Test
    public void testResolveMajorTypeWithDoubleCount_isCorrect() throws Exception {
        final String type = "Double";
        ClassDecorator classDecorator = new ClassDecorator();
        classDecorator.setClassDecorator(new ClassDecorator());
        classDecorator.setClassDecorator(new ClassDecorator(type));
        assertEquals("List<List<" + type + ">>", classDecorator.getJavaItem());
    }

    @Test
    public void testResolveMajorTypeWithZeroCount_isCorrect() throws Exception {
        final String type = "Double";
        ClassDecorator classDecorator = new ClassDecorator(type);
        assertEquals(type, classDecorator.getJavaItem());
    }

    @Test
    public void testProceedField_isCorrect() throws Exception {
        assertEquals("CamelCaseField", classGenerateHelper.proceedField("camelCaseField"));
        assertEquals("CamelCaseField", classGenerateHelper.proceedField("_camelCaseField"));
        assertEquals("Field", classGenerateHelper.proceedField("field"));
        assertEquals("JsonMemberPrivate", classGenerateHelper.proceedField("private"));
        assertEquals("JsonMember3351231Yte",
                classGenerateHelper.proceedField("!!@##$%^$3351$%^&23^1_--=---___-_-yte"));
        assertEquals("ArrayOfJsonObjectsItem", classGenerateHelper.proceedField("Array_of_json_objectsItem"));
    }
}
