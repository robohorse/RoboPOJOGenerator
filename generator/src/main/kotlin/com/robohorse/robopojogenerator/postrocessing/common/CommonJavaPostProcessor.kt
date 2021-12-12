package com.robohorse.robopojogenerator.postrocessing.common

import com.robohorse.robopojogenerator.properties.ClassItem
import com.robohorse.robopojogenerator.properties.templates.ClassTemplate
import com.robohorse.robopojogenerator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.utils.ClassTemplateHelper
import com.robohorse.robopojogenerator.models.FieldModel
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.models.Visibility

internal class CommonJavaPostProcessor(
    generateHelper: ClassGenerateHelper,
    classTemplateHelper: ClassTemplateHelper
) : JavaPostProcessor(generateHelper, classTemplateHelper) {

    override fun proceedClassBody(
        classItem: ClassItem,
        generationModel: GenerationModel
    ): String {
        val classBodyBuilder = StringBuilder()
        val classMethodBuilder = StringBuilder()
        val classFields = classItem.classFields
        with(classBodyBuilder) {
            for (objectName in classFields.keys) {
                val classItemValue = classFields[objectName]?.getJavaItem(
                    primitive = generationModel.javaPrimitives
                )
                val itemNameFormatted = generateHelper.formatClassField(objectName)
                append(
                    classTemplateHelper.createField(
                        FieldModel(
                            classType = classItemValue,
                            fieldNameFormatted = itemNameFormatted,
                            fieldName = objectName,
                            annotation = classItem.annotation,
                            visibility = if (generationModel.useLombokValue) Visibility.NONE else Visibility.PRIVATE
                        )
                    )
                )
            }
            for (objectName in classFields.keys) {
                val classItemValue = classFields[objectName]?.getJavaItem(
                    primitive = generationModel.javaPrimitives
                )
                val itemNameFormatted = generateHelper.formatClassField(objectName)
                if (generationModel.useSetters) {
                    append(ClassTemplate.NEW_LINE)
                    append(
                        classTemplateHelper.createSetter(
                            itemNameFormatted,
                            classItemValue
                        )
                    )
                }
                if (generationModel.useGetters) {
                    append(ClassTemplate.NEW_LINE)
                    append(
                        classTemplateHelper.createGetter(
                            itemNameFormatted,
                            classItemValue
                        )
                    )
                }
            }
            if (generationModel.useStrings) {
                append(ClassTemplate.NEW_LINE)
                append(
                    classTemplateHelper.createToString(
                        classItem
                    )
                )
            }
            append(classMethodBuilder)
            return toString()
        }
    }

    override fun createClassTemplate(
        classItem: ClassItem,
        classBody: String?,
        generationModel: GenerationModel
    ) = classTemplateHelper.createClassBody(classItem, classBody)
}
