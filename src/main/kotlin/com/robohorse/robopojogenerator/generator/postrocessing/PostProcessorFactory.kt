package com.robohorse.robopojogenerator.generator.postrocessing

import com.robohorse.robopojogenerator.generator.postrocessing.common.AutoValueClassPostProcessor
import com.robohorse.robopojogenerator.generator.postrocessing.common.CommonJavaPostProcessor
import com.robohorse.robopojogenerator.generator.postrocessing.common.KotlinDataClassPostProcessor
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.view.FrameworkVW.AutoValue

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
