package com.robohorse.robopojogenerator.postrocessing.common

import com.robohorse.robopojogenerator.models.FieldModel
import com.robohorse.robopojogenerator.models.FrameworkVW.*
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.postrocessing.BasePostProcessor
import com.robohorse.robopojogenerator.postrocessing.utils.MoshiAnnotationsProcessor
import com.robohorse.robopojogenerator.properties.ClassItem
import com.robohorse.robopojogenerator.properties.annotations.KotlinAnnotations
import com.robohorse.robopojogenerator.properties.templates.ClassTemplate
import com.robohorse.robopojogenerator.properties.templates.ImportsTemplate
import com.robohorse.robopojogenerator.properties.templates.PARCELABLE_ANDROID
import com.robohorse.robopojogenerator.properties.templates.PARCELIZE_KOTLINX
import com.robohorse.robopojogenerator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.utils.ClassTemplateHelper

internal class KotlinDataClassPostProcessor(
    generateHelper: ClassGenerateHelper,
    classTemplateHelper: ClassTemplateHelper,
    private val moshiAnnotationsProcessor: MoshiAnnotationsProcessor
) : BasePostProcessor(generateHelper, classTemplateHelper) {

    override fun proceedClassImports(
        imports: HashSet<String>,
        generationModel: GenerationModel
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

    override fun proceedClassBody(
        classItem: ClassItem,
        generationModel: GenerationModel
    ): String {
        val classBodyBuilder = StringBuilder()
        val classFields = classItem.classFields
        for (objectName in classFields.keys) {
            classBodyBuilder.append(
                classTemplateHelper.createKotlinDataClassField(
                    generationModel,
                    FieldModel(
                        classType = classFields[objectName]?.getKotlinItem(),
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

    override fun createClassItemText(
        packagePath: String?,
        imports: String?,
        classTemplate: String?
    ) = classTemplateHelper
        .createClassItemWithoutSemicolon(
            packagePath,
            imports,
            classTemplate
        )

    override fun applyAnnotations(
        generationModel: GenerationModel,
        classItem: ClassItem
    ) = when (generationModel.annotationEnum) {
        is Gson -> {
            generateHelper.setAnnotations(
                classItem,
                KotlinAnnotations.GSON.classAnnotation,
                KotlinAnnotations.GSON.annotation,
                ImportsTemplate.GSON.imports
            )
        }

        is KotlinX -> {
            generateHelper.setAnnotations(
                classItem,
                KotlinAnnotations.KOTLIN_X.classAnnotation,
                KotlinAnnotations.KOTLIN_X.annotation,
                ImportsTemplate.KOTLIN_X.imports
            )
        }

        is LoganSquare -> {
            generateHelper.setAnnotations(
                classItem,
                KotlinAnnotations.LOGAN_SQUARE.classAnnotation,
                KotlinAnnotations.LOGAN_SQUARE.annotation,
                ImportsTemplate.LOGAN_SQUARE.imports
            )
        }

        is Jackson -> {
            generateHelper.setAnnotations(
                classItem,
                KotlinAnnotations.JACKSON.classAnnotation,
                KotlinAnnotations.JACKSON.annotation,
                ImportsTemplate.JACKSON.imports
            )
        }

        is FastJson -> {
            generateHelper.setAnnotations(
                classItem,
                KotlinAnnotations.FAST_JSON.classAnnotation,
                KotlinAnnotations.FAST_JSON.annotation,
                ImportsTemplate.FAST_JSON.imports
            )
        }

        is Jakatra -> {
            generateHelper.setAnnotations(
                classItem,
                KotlinAnnotations.JAKATRA.classAnnotation,
                KotlinAnnotations.JAKATRA.annotation,
                ImportsTemplate.JAKATRA.imports
            )
        }

        is Moshi -> moshiAnnotationsProcessor.applyAnnotations(generationModel, classItem)

        else -> {
            // NO OP
        }
    }

    override fun createClassTemplate(
        classItem: ClassItem,
        classBody: String?,
        generationModel: GenerationModel
    ) = classTemplateHelper.createClassBodyKotlinDataClass(
        classItem,
        classBody,
        generationModel
    )
}
