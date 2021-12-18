package com.robohorse.robopojogenerator.postrocessing

import com.robohorse.robopojogenerator.models.FrameworkVW.AutoValue
import com.robohorse.robopojogenerator.models.FrameworkVW.JavaRecords
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.postrocessing.common.AutoValueClassPostProcessor
import com.robohorse.robopojogenerator.postrocessing.common.CommonJavaPostProcessor
import com.robohorse.robopojogenerator.postrocessing.common.JavaRecordsPostProcessor
import com.robohorse.robopojogenerator.postrocessing.common.KotlinDataClassPostProcessor

internal class PostProcessorFactory(
    private val kotlinDataClassPostProcessor: KotlinDataClassPostProcessor,
    private val autoValueClassPostProcessor: AutoValueClassPostProcessor,
    private val commonJavaPostProcessor: CommonJavaPostProcessor,
    private val javaRecordsPostProcessor: JavaRecordsPostProcessor
) {
    fun createPostProcessor(
        generationModel: GenerationModel
    ): BasePostProcessor = with(generationModel) {
        if (useKotlin) {
            kotlinDataClassPostProcessor
        } else if (annotationEnum is AutoValue) {
            autoValueClassPostProcessor
        } else if (annotationEnum is JavaRecords) {
            javaRecordsPostProcessor
        } else {
            commonJavaPostProcessor
        }
    }
}
