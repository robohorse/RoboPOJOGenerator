package com.robohorse.robopojogenerator.postrocessing.common

import com.robohorse.robopojogenerator.models.FrameworkVW.AutoValue
import com.robohorse.robopojogenerator.models.FrameworkVW.FastJson
import com.robohorse.robopojogenerator.models.FrameworkVW.Gson
import com.robohorse.robopojogenerator.models.FrameworkVW.Jackson
import com.robohorse.robopojogenerator.models.FrameworkVW.LoganSquare
import com.robohorse.robopojogenerator.models.FrameworkVW.Moshi
import com.robohorse.robopojogenerator.models.FrameworkVW.None
import com.robohorse.robopojogenerator.models.FrameworkVW.NoneLombok
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.postrocessing.BasePostProcessor
import com.robohorse.robopojogenerator.properties.ClassItem
import com.robohorse.robopojogenerator.properties.annotations.PojoAnnotations
import com.robohorse.robopojogenerator.properties.templates.ImportsTemplate
import com.robohorse.robopojogenerator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.utils.ClassTemplateHelper

internal abstract class JavaPostProcessor(
    generateHelper: ClassGenerateHelper,
    classTemplateHelper: ClassTemplateHelper
) : BasePostProcessor(generateHelper, classTemplateHelper) {

    override fun applyAnnotations(
        generationModel: GenerationModel,
        classItem: ClassItem
    ) {
        when (generationModel.annotationEnum) {
            is Gson -> {
                generateHelper.setAnnotations(
                    classItem,
                    PojoAnnotations.GSON.classAnnotation,
                    PojoAnnotations.GSON.annotation,
                    ImportsTemplate.GSON.imports
                )
            }
            is LoganSquare -> {
                generateHelper.setAnnotations(
                    classItem,
                    PojoAnnotations.LOGAN_SQUARE.classAnnotation,
                    PojoAnnotations.LOGAN_SQUARE.annotation,
                    ImportsTemplate.LOGAN_SQUARE.imports
                )
            }
            is Jackson -> {
                generateHelper.setAnnotations(
                    classItem,
                    PojoAnnotations.JACKSON.classAnnotation,
                    PojoAnnotations.JACKSON.annotation,
                    ImportsTemplate.JACKSON.imports
                )
            }
            is FastJson -> {
                generateHelper.setAnnotations(
                    classItem,
                    PojoAnnotations.FAST_JSON.classAnnotation,
                    PojoAnnotations.FAST_JSON.annotation,
                    ImportsTemplate.FAST_JSON.imports
                )
            }
            is AutoValue -> {
                generateHelper.setAnnotations(
                    classItem,
                    PojoAnnotations.AUTO_VALUE_GSON.classAnnotation,
                    PojoAnnotations.AUTO_VALUE_GSON.annotation,
                    ImportsTemplate.AUTO_VALUE_GSON.imports
                )
            }
            is Moshi -> {
                generateHelper.setAnnotations(
                    classItem,
                    PojoAnnotations.MOSHI.classAnnotation,
                    PojoAnnotations.MOSHI.annotation,
                    ImportsTemplate.MOSHI.imports
                )
            }
            is NoneLombok -> {
                val annotations = PojoAnnotations.Lombok(generationModel.useLombokValue)
                val importsTemplate = ImportsTemplate.Lombok(generationModel.useLombokValue)
                generateHelper.setAnnotations(
                    classItem,
                    annotations.classAnnotation,
                    annotations.annotation,
                    importsTemplate.imports
                )
            }
            is None -> { // NO OP
            }
        }
    }
}
