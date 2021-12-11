package com.robohorse.robopojogenerator.generator.postrocessing.common

import com.robohorse.robopojogenerator.generator.consts.common.ClassItem
import com.robohorse.robopojogenerator.generator.consts.templates.ClassTemplate
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.generator.utils.ClassTemplateHelper
import com.robohorse.robopojogenerator.models.FieldModel
import com.robohorse.robopojogenerator.models.GenerationModel

internal class AutoValueClassPostProcessor(
    generateHelper: ClassGenerateHelper,
    classTemplateHelper: ClassTemplateHelper
) : JavaPostProcessor(generateHelper, classTemplateHelper) {

    override fun proceedClassBody(
        classItem: ClassItem,
        generationModel: GenerationModel
    ): String {
        val classBodyBuilder = StringBuilder()
        val classFields = classItem.classFields
        with(classBodyBuilder) {
            for (objectName in classFields.keys) {
                append(
                    classTemplateHelper.createAutoValueField(
                        FieldModel(
                            classType = classFields[objectName]?.getJavaItem(),
                            annotation = classItem.annotation,
                            fieldName = objectName,
                            fieldNameFormatted = generateHelper.formatClassField(objectName)
                        )
                    )
                )
            }
            append(ClassTemplate.NEW_LINE)
            append(classTemplateHelper.createTypeAdapter(classItem))
            return toString()
        }
    }

    override fun createClassTemplate(
        classItem: ClassItem,
        classBody: String?,
        generationModel: GenerationModel
    ) = classTemplateHelper.createClassBodyAbstract(classItem, classBody)
}
