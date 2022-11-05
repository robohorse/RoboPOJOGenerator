package com.robohorse.robopojogenerator.postrocessing.utils

import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.properties.ClassItem
import com.robohorse.robopojogenerator.properties.annotations.KotlinAnnotations
import com.robohorse.robopojogenerator.properties.templates.ImportsTemplate
import com.robohorse.robopojogenerator.utils.ClassGenerateHelper

internal class MoshiAnnotationsProcessor(
    private val generateHelper: ClassGenerateHelper
) {

    fun applyAnnotations(
        generationModel: GenerationModel,
        classItem: ClassItem
    ) = with(ImportsTemplate.MOSHI()) {
        with(KotlinAnnotations.MOSHI()) {
            generateHelper.setAnnotations(
                classItem,
                if (generationModel.useMoshiAdapter) {
                    adapterClassAnnotation
                } else {
                    classAnnotation
                },
                annotation,
                if (generationModel.useMoshiAdapter) {
                    imports + jsonClassAnnotation
                } else {
                    imports
                }
            )
        }
    }
}
