package com.robohorse.robopojogenerator.generator.postrocessing.common

import com.robohorse.robopojogenerator.generator.consts.annotations.AnnotationEnum
import com.robohorse.robopojogenerator.generator.consts.annotations.KotlinAnnotations
import com.robohorse.robopojogenerator.generator.consts.common.ClassItem
import com.robohorse.robopojogenerator.generator.consts.templates.ClassTemplate
import com.robohorse.robopojogenerator.generator.consts.templates.ImportsTemplate
import com.robohorse.robopojogenerator.generator.postrocessing.BasePostProcessor
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.generator.utils.ClassTemplateHelper
import com.robohorse.robopojogenerator.models.FieldModel
import com.robohorse.robopojogenerator.models.GenerationModel

class KotlinDataClassPostProcessor(
        generateHelper: ClassGenerateHelper,
        classTemplateHelper: ClassTemplateHelper
) : BasePostProcessor(generateHelper, classTemplateHelper) {

    override fun proceedClassImports(
            classItem: ClassItem
    ): StringBuilder {
        classItem.classImports.remove(ImportsTemplate.LIST)
        val importsBuilder = StringBuilder()
        for (importItem in classItem.classImports) {
            importsBuilder.append(importItem.replace(";", ""))
            importsBuilder.append(ClassTemplate.NEW_LINE)
        }
        return importsBuilder
    }

    override fun proceedClassBody(classItem: ClassItem, generationModel: GenerationModel): String {
        val classBodyBuilder = StringBuilder()
        val classFields = classItem.classFields
        for (objectName in classFields.keys) {
            classBodyBuilder.append(classTemplateHelper.createKotlinDataClassField(
                    FieldModel(
                            classType = classFields[objectName]?.getKotlinItem(),
                            annotation = classItem.annotation,
                            fieldName = objectName,
                            fieldNameFormatted = generateHelper.formatClassField(objectName)
                    )
            ))
        }
        generateHelper.updateClassModel(classBodyBuilder)
        return classBodyBuilder.toString()
    }

    override fun createClassItemText(packagePath: String?, imports: String?, classTemplate: String?): String {
        return classTemplateHelper
                .createClassItemWithoutSemicolon(packagePath,
                        imports,
                        classTemplate)
    }

    override fun applyAnnotations(item: AnnotationEnum, classItem: ClassItem) {
        when (item) {
            AnnotationEnum.GSON -> {
                generateHelper.setAnnotations(classItem,
                        KotlinAnnotations.GSON.classAnnotation,
                        KotlinAnnotations.GSON.annotation,
                        ImportsTemplate.GSON.imports)
            }
            AnnotationEnum.LOGAN_SQUARE -> {
                generateHelper.setAnnotations(classItem,
                        KotlinAnnotations.LOGAN_SQUARE.classAnnotation,
                        KotlinAnnotations.LOGAN_SQUARE.annotation,
                        ImportsTemplate.LOGAN_SQUARE.imports)
            }
            AnnotationEnum.JACKSON -> {
                generateHelper.setAnnotations(classItem,
                        KotlinAnnotations.JACKSON.classAnnotation,
                        KotlinAnnotations.JACKSON.annotation,
                        ImportsTemplate.JACKSON.imports)
            }
            AnnotationEnum.FAST_JSON -> {
                generateHelper.setAnnotations(classItem,
                        KotlinAnnotations.FAST_JSON.classAnnotation,
                        KotlinAnnotations.FAST_JSON.annotation,
                        ImportsTemplate.FAST_JSON.imports)
            }
            AnnotationEnum.MOSHI -> {
                generateHelper.setAnnotations(classItem,
                        KotlinAnnotations.MOSHI.classAnnotation,
                        KotlinAnnotations.MOSHI.annotation,
                        ImportsTemplate.MOSHI.imports)
            }
        }
    }

    override fun createClassTemplate(classItem: ClassItem, classBody: String?): String {
        return classTemplateHelper.createClassBodyKotlinDataClass(classItem, classBody)
    }
}
