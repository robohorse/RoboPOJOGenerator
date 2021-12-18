package com.robohorse.robopojogenerator.postrocessing.common

import com.robohorse.robopojogenerator.models.FieldModel
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.properties.ClassItem
import com.robohorse.robopojogenerator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.utils.ClassTemplateHelper

internal class JavaRecordsPostProcessor(
    generateHelper: ClassGenerateHelper,
    classTemplateHelper: ClassTemplateHelper
) : JavaPostProcessor(generateHelper, classTemplateHelper) {

    override fun proceedClassBody(classItem: ClassItem, generationModel: GenerationModel): String {
        val classBodyBuilder = StringBuilder()
        val classFields = classItem.classFields
        for (objectName in classFields.keys) {
            val classItemValue = classFields[objectName]?.getJavaItem(
                primitive = generationModel.javaPrimitives
            )
            classBodyBuilder.append(
                classTemplateHelper.createJavaRecordClassField(
                    FieldModel(
                        classType = classItemValue,
                        annotation = classItem.annotation,
                        fieldName = objectName,
                        fieldNameFormatted = generateHelper.formatClassField(objectName)
                    )
                )
            )
        }
        generateHelper.updateClassModel(classBodyBuilder)
        return classBodyBuilder.toString()
    }

    override fun createClassTemplate(
        classItem: ClassItem,
        classBody: String?,
        generationModel: GenerationModel
    ) = classTemplateHelper.createClassBodyRecords(classItem, classBody)
}
