package com.robohorse.robopojogenerator.generator.postrocessing.common

import com.robohorse.robopojogenerator.generator.consts.annotations.KotlinAnnotations
import com.robohorse.robopojogenerator.generator.consts.common.ClassItem
import com.robohorse.robopojogenerator.generator.consts.templates.ClassTemplate
import com.robohorse.robopojogenerator.generator.consts.templates.ImportsTemplate
import com.robohorse.robopojogenerator.generator.consts.templates.PARCELABLE_ANDROID
import com.robohorse.robopojogenerator.generator.consts.templates.PARCELIZE_KOTLINX
import com.robohorse.robopojogenerator.generator.postrocessing.BasePostProcessor
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.generator.utils.ClassTemplateHelper
import com.robohorse.robopojogenerator.models.FieldModel
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.view.FrameworkVW.*
import java.util.*

class KotlinDataClassPostProcessor(
        generateHelper: ClassGenerateHelper,
        classTemplateHelper: ClassTemplateHelper
) : BasePostProcessor(generateHelper, classTemplateHelper) {

    override fun proceedClassImports(
            imports: HashSet<String>, generationModel: GenerationModel
    ): StringBuilder {
        imports.remove(ImportsTemplate.LIST)
        if (generationModel.useKotlinParcelable) {
            imports.add(PARCELABLE_ANDROID)
            imports.add(PARCELIZE_KOTLINX)
        }
        val importsBuilder = StringBuilder()
        for (importItem in imports) {
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
                    generationModel,
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

    override fun applyAnnotations(
            generationModel: GenerationModel,
            classItem: ClassItem
    ) {
        when (generationModel.annotationEnum) {
            is Gson -> {
                generateHelper.setAnnotations(classItem,
                        KotlinAnnotations.GSON.classAnnotation,
                        KotlinAnnotations.GSON.annotation,
                        ImportsTemplate.GSON.imports)
            }
            is LoganSquare -> {
                generateHelper.setAnnotations(classItem,
                        KotlinAnnotations.LOGAN_SQUARE.classAnnotation,
                        KotlinAnnotations.LOGAN_SQUARE.annotation,
                        ImportsTemplate.LOGAN_SQUARE.imports)
            }
            is Jackson -> {
                generateHelper.setAnnotations(classItem,
                        KotlinAnnotations.JACKSON.classAnnotation,
                        KotlinAnnotations.JACKSON.annotation,
                        ImportsTemplate.JACKSON.imports)
            }
            is FastJson -> {
                generateHelper.setAnnotations(classItem,
                        KotlinAnnotations.FAST_JSON.classAnnotation,
                        KotlinAnnotations.FAST_JSON.annotation,
                        ImportsTemplate.FAST_JSON.imports)
            }
            is Moshi -> {
                generateHelper.setAnnotations(classItem,
                        KotlinAnnotations.MOSHI.classAnnotation,
                        KotlinAnnotations.MOSHI.annotation,
                        ImportsTemplate.MOSHI.imports)
            }
            else -> {
                //NO OP
            }
        }
    }

    override fun createClassTemplate(
            classItem: ClassItem, classBody: String?, generationModel: GenerationModel
    ): String {
        return classTemplateHelper.createClassBodyKotlinDataClass(
                classItem,
                classBody,
                generationModel.useKotlinParcelable
        )
    }
}
