package com.robohorse.robopojogenerator.generator.utils

import com.robohorse.robopojogenerator.errors.RoboPluginException
import com.robohorse.robopojogenerator.generator.common.ClassEnum
import com.robohorse.robopojogenerator.generator.common.common.ClassField
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class ClassGenerateHelperTest {
    private val classGenerateHelper = ClassGenerateHelper()

    @Test
    fun classNameModification_isCorrect() {
        assertEquals("Item", classGenerateHelper.formatClassName("item"))
    }

    @Test
    fun testModification_isCorrect() {
        var exception: Exception? = null
        try {
            classGenerateHelper.validateJsonContent("{\"data\":1}")
        } catch (e: RoboPluginException) {
            exception = e
        }
        assertNull(exception)
    }

    @Test
    fun testModificationArray_isCorrect() {
        var exception: Exception? = null
        try {
            classGenerateHelper.validateJsonContent("[{\"data\":1}]")
        } catch (e: RoboPluginException) {
            exception = e
        }
        assertNull(exception)
    }

    @Test
    fun testModificationArrayToJson_isCorrect() {
        val jsonItem = "{\"data\":1}"
        var exception: Exception? = null
        try {
            val result = classGenerateHelper.validateJsonContent("[$jsonItem]")
            assertEquals(result, jsonItem)
        } catch (e: RoboPluginException) {
            exception = e
        }
        assertNull(exception)
    }

    @Test
    fun testModificationArray_isCorrect_onError() {
        var exception: Exception? = null
        try {
            classGenerateHelper.validateJsonContent("[1,2]")
        } catch (e: RoboPluginException) {
            exception = e
        }
        assertNotNull(exception)
    }

    @Test
    fun testModificationWithWrongStructure_isCorrect() {
        var exception: Exception? = null
        try {
            classGenerateHelper.validateJsonContent("1123")
        } catch (e: RoboPluginException) {
            exception = e
        }
        assertNotNull(exception)
    }

    @Test
    fun testValidateClassName_isCorrect() {
        var exception: Exception? = null
        try {
            classGenerateHelper.validateClassName("MySuperAwesomeClass")
        } catch (e: RoboPluginException) {
            exception = e
        }
        assertNull(exception)
    }

    @Test
    fun testValidateClassNameWithWrongName_isCorrect() {
        var exception: Exception? = null
        try {
            classGenerateHelper.validateClassName(".MySuperAwesomeClass23")
        } catch (e: RoboPluginException) {
            exception = e
        }
        assertNotNull(exception)
    }

    @Test
    fun testResolveMajorTypeWithSingleCount_isCorrect() {
        val type = ClassEnum.DOUBLE.boxed
        val classField = ClassField()
        classField.classField = ClassField(ClassEnum.DOUBLE)
        assertEquals("List<$type>", classField.getJavaItem())
    }

    @Test
    fun testResolveMajorTypeWithDoubleCount_isCorrect() {
        val type = ClassEnum.DOUBLE.boxed
        val classField = ClassField()
        classField.classField = ClassField()
        classField.classField?.classField = ClassField(ClassEnum.DOUBLE)
        assertEquals("List<List<$type>>", classField.getJavaItem())
    }

    @Test
    fun testResolveMajorTypeWithZeroCount_isCorrect() {
        val type = ClassEnum.DOUBLE.primitive
        val classField = ClassField(ClassEnum.DOUBLE)
        assertEquals(type, classField.getJavaItem())
    }

    @Test
    fun testProceedField_isCorrect() {
        assertEquals("CamelCaseField", classGenerateHelper.proceedField("camelCaseField"))
        assertEquals("CamelCaseField", classGenerateHelper.proceedField("_camelCaseField"))
        assertEquals("Field", classGenerateHelper.proceedField("field"))
        assertEquals("JsonMemberPrivate", classGenerateHelper.proceedField("private"))
        assertEquals(
            "JsonMember3351231Yte",
            classGenerateHelper.proceedField("!!@##$%^$3351$%^&23^1_--=---___-_-yte")
        )
        assertEquals("ArrayOfJsonObjectsItem", classGenerateHelper.proceedField("Array_of_json_objectsItem"))
    }
}
