package com.robohorse.robopojogenerator.postrocessing

import com.robohorse.robopojogenerator.models.FrameworkVW.AutoValue
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.postrocessing.common.AutoValueClassPostProcessor
import com.robohorse.robopojogenerator.postrocessing.common.CommonJavaPostProcessor
import com.robohorse.robopojogenerator.postrocessing.common.KotlinDataClassPostProcessor

internal class PostProcessorFactory(
    private val kotlinDataClassPostProcessor: KotlinDataClassPostProcessor,
    private val autoValueClassPostProcessor: AutoValueClassPostProcessor,
    private val commonJavaPostProcessor: CommonJavaPostProcessor
) {
    fun createPostProcessor(
        generationModel: GenerationModel
    ): BasePostProcessor = with(generationModel) {
        if (useKotlin) {
            kotlinDataClassPostProcessor
        } else if (annotationEnum is AutoValue) {
            autoValueClassPostProcessor
        } else {
            commonJavaPostProcessor
        }
    }
}
