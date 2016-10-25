package com.robohorse.robopojogenerator.generator.utils;

import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.models.InnerArrayModel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by vadim on 28.09.16.
 */
public class ClassGenerateHelperTest {
    private ClassGenerateHelper classGenerateHelper = new ClassGenerateHelper();

    @Test
    public void getClassNameModification_isCorrect() throws Exception {
        assertEquals("Item", classGenerateHelper.getClassName("item"));
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
        InnerArrayModel innerArrayModel = new InnerArrayModel();
        innerArrayModel.increaseCount();

        innerArrayModel.setMajorType(type);
        assertEquals("List<" + type + ">", classGenerateHelper.resolveMajorType(innerArrayModel));
    }

    @Test
    public void testResolveMajorTypeWithDoubleCount_isCorrect() throws Exception {
        final String type = "Double";
        InnerArrayModel innerArrayModel = new InnerArrayModel();
        innerArrayModel.increaseCount();
        innerArrayModel.increaseCount();

        innerArrayModel.setMajorType(type);
        assertEquals("List<List<" + type + ">>", classGenerateHelper.resolveMajorType(innerArrayModel));
    }

    @Test
    public void testResolveMajorTypeWithZeroCount_isCorrect() throws Exception {
        final String type = "Double";
        InnerArrayModel innerArrayModel = new InnerArrayModel();

        innerArrayModel.setMajorType(type);
        assertEquals(type, classGenerateHelper.resolveMajorType(innerArrayModel));
    }

    @Test
    public void testUpdateKotlinType() throws Exception {
        final String nestedListType = "List<List<List<Integer>>>";
        final String nestedListTarget = "List<List<List<Int?>?>?>";

        final String nestedListObjectType = "List<List<Object>>";
        final String nestedListObjectTarget = "List<List<Any?>?>";

        final String nestedListObjectItemType = "List<List<ObjectItem>>";
        final String nestedListObjectItemTarget = "List<List<ObjectItem?>?>";

        final String nestedListIntegerItemType = "List<List<IntegerItem>>";
        final String nestedListIntegerItemTarget = "List<List<IntegerItem?>?>";

        final String primitiveType = "int";
        final String primitiveTarget = "Int";

        final String objectType = "Object";
        final String objectTarget = "Any";

        assertEquals(nestedListTarget, classGenerateHelper.updateKotlinType(nestedListType));
        assertEquals(nestedListObjectTarget, classGenerateHelper.updateKotlinType(nestedListObjectType));
        assertEquals(nestedListObjectItemTarget, classGenerateHelper.updateKotlinType(nestedListObjectItemType));
        assertEquals(nestedListIntegerItemTarget, classGenerateHelper.updateKotlinType(nestedListIntegerItemType));
        assertEquals(primitiveTarget, classGenerateHelper.updateKotlinType(primitiveType));
        assertEquals(objectTarget, classGenerateHelper.updateKotlinType(objectType));
    }
}
