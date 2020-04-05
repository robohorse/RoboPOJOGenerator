package com.robohorse.robopojogenerator.generator.postrocessing

import com.robohorse.robopojogenerator.generator.consts.annotations.AnnotationEnum.AUTO_VALUE_GSON
import com.robohorse.robopojogenerator.generator.postrocessing.common.AutoValueClassPostProcessor
import com.robohorse.robopojogenerator.generator.postrocessing.common.CommonJavaPostProcessor
import com.robohorse.robopojogenerator.generator.postrocessing.common.KotlinDataClassPostProcessor
import com.robohorse.robopojogenerator.models.GenerationModel

class PostProcessorFactory(
        private val kotlinDataClassPostProcessor: KotlinDataClassPostProcessor,
        private val autoValueClassPostProcessor: AutoValueClassPostProcessor,
        private val commonJavaPostProcessor: CommonJavaPostProcessor
) {
    fun createPostProcessor(
            generationModel: GenerationModel
    ): BasePostProcessor = with(generationModel) {
        if (useKotlin) {
            kotlinDataClassPostProcessor
        } else if (annotationEnum === AUTO_VALUE_GSON) {
            autoValueClassPostProcessor
        } else {
            commonJavaPostProcessor
        }
    }
}
