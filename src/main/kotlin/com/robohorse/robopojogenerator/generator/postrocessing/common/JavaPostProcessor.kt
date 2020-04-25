package com.robohorse.robopojogenerator.generator.postrocessing.common

import com.robohorse.robopojogenerator.generator.consts.annotations.PojoAnnotations
import com.robohorse.robopojogenerator.generator.consts.common.ClassItem
import com.robohorse.robopojogenerator.generator.consts.templates.ImportsTemplate
import com.robohorse.robopojogenerator.generator.postrocessing.BasePostProcessor
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.generator.utils.ClassTemplateHelper
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.view.FrameworkVW.*

abstract class JavaPostProcessor(
        generateHelper: ClassGenerateHelper,
        classTemplateHelper: ClassTemplateHelper
) : BasePostProcessor(generateHelper, classTemplateHelper) {

    override fun applyAnnotations(
            generationModel: GenerationModel,
            classItem: ClassItem
    ) {
        when (generationModel.annotationEnum) {
            is Gson -> {
                generateHelper.setAnnotations(classItem,
                        PojoAnnotations.GSON.classAnnotation,
                        PojoAnnotations.GSON.annotation,
                        ImportsTemplate.GSON.imports)
            }
            is LoganSquare -> {
                generateHelper.setAnnotations(classItem,
                        PojoAnnotations.LOGAN_SQUARE.classAnnotation,
                        PojoAnnotations.LOGAN_SQUARE.annotation,
                        ImportsTemplate.LOGAN_SQUARE.imports)
            }
            is Jackson -> {
                generateHelper.setAnnotations(classItem,
                        PojoAnnotations.JACKSON.classAnnotation,
                        PojoAnnotations.JACKSON.annotation,
                        ImportsTemplate.JACKSON.imports)
            }
            is FastJson -> {
                generateHelper.setAnnotations(classItem,
                        PojoAnnotations.FAST_JSON.classAnnotation,
                        PojoAnnotations.FAST_JSON.annotation,
                        ImportsTemplate.FAST_JSON.imports)
            }
            is AutoValue -> {
                generateHelper.setAnnotations(classItem,
                        PojoAnnotations.AUTO_VALUE_GSON.classAnnotation,
                        PojoAnnotations.AUTO_VALUE_GSON.annotation,
                        ImportsTemplate.AUTO_VALUE_GSON.imports)
            }
            is Moshi -> {
                generateHelper.setAnnotations(classItem,
                        PojoAnnotations.MOSHI.classAnnotation,
                        PojoAnnotations.MOSHI.annotation,
                        ImportsTemplate.MOSHI.imports)
            }
            is None -> {// NO OP
            }
        }
    }
}
