package com.robohorse.robopojogenerator.generator.postrocessing.common

import com.robohorse.robopojogenerator.generator.consts.common.ClassItem
import com.robohorse.robopojogenerator.generator.consts.templates.ClassTemplate
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.generator.utils.ClassTemplateHelper
import com.robohorse.robopojogenerator.models.FieldModel
import com.robohorse.robopojogenerator.models.GenerationModel

class CommonJavaPostProcessor(
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
                val classItemValue = classFields[objectName]?.getJavaItem()
                val itemNameFormatted = generateHelper.formatClassField(objectName)
                append(classTemplateHelper.createFiled(
                        FieldModel(
                                classType = classItemValue,
                                fieldNameFormatted = itemNameFormatted,
                                fieldName = objectName,
                                annotation = classItem.annotation
                        )
                ))
            }
            for (objectName in classFields.keys) {
                val classItemValue = classFields[objectName]?.getJavaItem()
                val itemNameFormatted = generateHelper.formatClassField(objectName)
                if (generationModel.useSetters) {
                    append(ClassTemplate.NEW_LINE)
                    append(classTemplateHelper.createSetter(
                            itemNameFormatted,
                            classItemValue))
                }
                if (generationModel.useGetters) {
                    append(ClassTemplate.NEW_LINE)
                    append(classTemplateHelper.createGetter(
                            itemNameFormatted,
                            classItemValue))
                }
            }
            if (generationModel.useStrings) {
                append(ClassTemplate.NEW_LINE)
                append(classTemplateHelper.createToString(
                        classItem
                ))
            }
            append(classMethodBuilder)
            return toString()
        }
    }

    override fun createClassTemplate(
            classItem: ClassItem, classBody: String?, generationModel: GenerationModel
    ) = classTemplateHelper.createClassBody(classItem, classBody)
}
