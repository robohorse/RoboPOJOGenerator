package com.robohorse.robopojogenerator.generator.utils

import com.robohorse.robopojogenerator.generator.consts.ClassEnum
import com.robohorse.robopojogenerator.generator.consts.common.ClassItem
import com.robohorse.robopojogenerator.generator.consts.templates.ClassTemplate
import com.robohorse.robopojogenerator.models.FieldModel

class ClassTemplateHelper(
        private val classGenerateHelper: ClassGenerateHelper
) {

    fun createSetter(field: String, type: String?) = String.format(
            ClassTemplate.SETTER,
            classGenerateHelper.upperCaseFirst(field),
            type,
            classGenerateHelper.lowerCaseFirst(field)
    )

    fun createGetter(field: String, type: String?) =
            String.format(
                    if (ClassEnum.BOOLEAN.primitive.equals(type, ignoreCase = true))
                        ClassTemplate.GETTER_BOOLEAN else ClassTemplate.GETTER,
                    classGenerateHelper.upperCaseFirst(field),
                    classGenerateHelper.lowerCaseFirst(field),
                    type
            )

    fun createToString(classItem: ClassItem) =
            String.format(
                    ClassTemplate.TO_STRING,
                    classItem.className,
                    generateToStingItem(classItem)
            )

    private fun generateToStingItem(classItem: ClassItem): String {
        var isFirstField = true
        val fieldToStringStatement = StringBuilder()
        val fields = classItem.classFields.keys
        for (field in fields) {
            fieldToStringStatement.append(
                    String.format(ClassTemplate.TO_STRING_LINE,
                            classGenerateHelper.lowerCaseFirst(field),
                            classGenerateHelper.formatClassField(field),
                            if (isFirstField) "" else ",")
            )
            if (isFirstField) {
                isFirstField = false
            }
        }
        return fieldToStringStatement.toString()
    }

    fun createFiled(model: FieldModel) =
            createAnnotatedField(model.fieldName, model.annotation, String.format(
                    ClassTemplate.FIELD,
                    model.classType,
                    model.fieldNameFormatted
            ))

    fun createAutoValueField(model: FieldModel) =
            createAnnotatedField(model.fieldName, model.annotation, String.format(
                    ClassTemplate.FIELD_AUTO_VALUE,
                    model.classType,
                    model.fieldNameFormatted
            ))

    fun createKotlinDataClassField(model: FieldModel) =
            createAnnotatedField(model.fieldName, model.annotation, String.format(
                    ClassTemplate.FIELD_KOTLIN_DTO,
                    model.fieldNameFormatted,
                    model.classType
            ).replace(">", "?>"))

    fun createClassBody(classItem: ClassItem, classBody: String?) =
            createClassBodyAnnotated(classItem, String.format(
                    ClassTemplate.CLASS_BODY,
                    classItem.className,
                    classBody
            ))

    fun createTypeAdapter(classItem: ClassItem) =
            String.format(ClassTemplate.TYPE_ADAPTER, classItem.className)

    fun createClassBodyAbstract(classItem: ClassItem, classBody: String?) =
            createClassBodyAnnotated(classItem, String.format(
                    ClassTemplate.CLASS_BODY_ABSTRACT,
                    classItem.className,
                    classBody
            ))

    fun createClassBodyKotlinDataClass(
            classItem: ClassItem,
            classBody: String?,
            parcelable: Boolean = false
    ) = createClassBodyAnnotated(classItem, String.format(
            if (parcelable) {
                ClassTemplate.CLASS_BODY_KOTLIN_DTO_PARCELABLE
            } else {
                ClassTemplate.CLASS_BODY_KOTLIN_DTO
            },
            classItem.className,
            classBody
    ))

    fun createClassItem(
            packagePath: String?,
            imports: String?,
            body: String?
    ) = if (packagePath?.isNotEmpty() == true) {
        if (null != imports && imports.isNotEmpty()) {
            String.format(
                    ClassTemplate.CLASS_ROOT_IMPORTS,
                    packagePath,
                    imports,
                    body
            )
        } else {
            String.format(
                    ClassTemplate.CLASS_ROOT,
                    packagePath,
                    body
            )
        }
    } else {
        String.format(ClassTemplate.CLASS_ROOT_NO_PACKAGE, body)
    }

    fun createClassItemWithoutSemicolon(
            packagePath: String?,
            imports: String?,
            body: String?
    ) = if (packagePath?.isNotEmpty() == true) {
        if (null != imports && imports.isNotEmpty()) {
            String.format(
                    ClassTemplate.CLASS_ROOT_IMPORTS_WITHOUT_SEMICOLON,
                    packagePath,
                    imports,
                    body
            )
        } else {
            String.format(
                    ClassTemplate.CLASS_ROOT_WITHOUT_SEMICOLON,
                    packagePath,
                    body
            )
        }
    } else {
        String.format(ClassTemplate.CLASS_ROOT_NO_PACKAGE, body)
    }

    private fun createClassBodyAnnotated(
            classItem: ClassItem,
            classItemBody: String
    ) = if (classItem.classAnnotation?.isNotEmpty() == true) {
        String.format(
                ClassTemplate.CLASS_BODY_ANNOTATED,
                classItem.classAnnotation,
                classItemBody
        )
    } else {
        classItemBody
    }

    private fun createAnnotatedField(
            name: String?,
            annotation: String?,
            field: String
    ) = if (null != annotation && annotation.isNotEmpty()) {
        String.format(
                ClassTemplate.FIELD_ANNOTATED,
                String.format(annotation, name),
                field
        )
    } else {
        field
    }
}
