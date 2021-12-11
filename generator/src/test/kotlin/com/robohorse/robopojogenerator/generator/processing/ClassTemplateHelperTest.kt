package com.robohorse.robopojogenerator.generator.processing

import com.robohorse.robopojogenerator.generator.common.templates.ClassTemplate
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.generator.utils.ClassTemplateHelper
import com.robohorse.robopojogenerator.models.FieldModel
import com.robohorse.robopojogenerator.models.Visibility
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ClassTemplateHelperTest {
    @RelaxedMockK
    lateinit var classGenerateHelper: ClassGenerateHelper

    @InjectMockKs
    lateinit var classTemplateHelper: ClassTemplateHelper

    @BeforeTest
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    fun testCreateSetter() {
        val field = "item"
        val type = "String"
        val fieldUpper = "Item"
        every { classGenerateHelper.upperCaseFirst(field) }.returns(fieldUpper)
        every { classGenerateHelper.lowerCaseFirst(field) }.returns(field)
        every { classGenerateHelper.formatClassField(field) }.returns(field)
        val target = (
            ClassTemplate.TAB + "public void set" + fieldUpper + "(" + type + " " + field + "){" +
                ClassTemplate.NEW_LINE +
                ClassTemplate.TAB + ClassTemplate.TAB + "this." + field + " = " + field + ";" +
                ClassTemplate.NEW_LINE +
                ClassTemplate.TAB + "}" +
                ClassTemplate.NEW_LINE
            )
        assertEquals(target, classTemplateHelper.createSetter(field, type))
    }

    @Test
    fun testCreateGetter() {
        val field = "item"
        val type = "String"
        val fieldUpper = "Item"
        every { classGenerateHelper.upperCaseFirst(field) }.returns(fieldUpper)
        every { classGenerateHelper.lowerCaseFirst(field) }.returns(field)
        every { classGenerateHelper.formatClassField(field) }.returns(field)
        val target = (
            ClassTemplate.TAB + "public " + type + " get" + fieldUpper + "(){" +
                ClassTemplate.NEW_LINE +
                ClassTemplate.TAB + ClassTemplate.TAB + "return " + field + ";" +
                ClassTemplate.NEW_LINE +
                ClassTemplate.TAB + "}" +
                ClassTemplate.NEW_LINE
            )
        assertEquals(target, classTemplateHelper.createGetter(field, type))
    }

    @Test
    fun testCreateGetterBoolean() {
        val field = "item"
        val type = "boolean"
        val fieldUpper = "Item"
        every { classGenerateHelper.upperCaseFirst(field) }.returns(fieldUpper)
        every { classGenerateHelper.lowerCaseFirst(field) }.returns(field)
        every { classGenerateHelper.formatClassField(field) }.returns(field)
        val target = (
            ClassTemplate.TAB + "public boolean is" + fieldUpper + "(){" +
                ClassTemplate.NEW_LINE +
                ClassTemplate.TAB + ClassTemplate.TAB + "return " + field + ";" +
                ClassTemplate.NEW_LINE +
                ClassTemplate.TAB + "}" +
                ClassTemplate.NEW_LINE
            )
        assertEquals(target, classTemplateHelper.createGetter(field, type))
    }

    @Test
    fun testCreateField() {
        val field = "item"
        val type = "boolean"
        val target = (
            ClassTemplate.TAB + "private " + type + " " + field + ";" +
                ClassTemplate.NEW_LINE
            )
        every { classGenerateHelper.formatClassField(field) }.returns(field)
        assertEquals(
            target,
            classTemplateHelper.createField(
                FieldModel(
                    classType = type,
                    fieldNameFormatted = field,
                    fieldName = field,
                    visibility = Visibility.PRIVATE
                )
            )
        )
    }

    @Test
    fun testCreateFieldGenerated() {
        val field = "item"
        val type = "boolean"
        val annotation = "@JsonField"
        val target = (
            ClassTemplate.NEW_LINE + ClassTemplate.TAB + annotation + ClassTemplate.NEW_LINE +
                ClassTemplate.TAB + "private " + type + " " + field + ";" +
                ClassTemplate.NEW_LINE
            )
        every { classGenerateHelper.formatClassField(field) }.returns(field)
        assertEquals(
            target,
            classTemplateHelper.createField(
                FieldModel(
                    classType = type,
                    fieldName = field,
                    fieldNameFormatted = field,
                    annotation = annotation,
                    visibility = Visibility.PRIVATE
                )
            )
        )
    }

    @Test
    fun testCreateFieldWithoutVisibility() {
        val field = "item"
        val type = "boolean"
        val target = (ClassTemplate.TAB + type + " " + field + ";" + ClassTemplate.NEW_LINE)
        every { classGenerateHelper.formatClassField(field) }.returns(field)
        assertEquals(
            target,
            classTemplateHelper.createField(
                FieldModel(
                    classType = type,
                    fieldNameFormatted = field,
                    fieldName = field,
                    visibility = Visibility.NONE
                )
            )
        )
    }
}
