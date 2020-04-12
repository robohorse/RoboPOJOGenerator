package com.robohorse.robopojogenerator.generator.postprocessing

import com.robohorse.robopojogenerator.generator.consts.annotations.AnnotationEnum
import com.robohorse.robopojogenerator.generator.postrocessing.PostProcessorFactory
import com.robohorse.robopojogenerator.generator.postrocessing.common.AutoValueClassPostProcessor
import com.robohorse.robopojogenerator.generator.postrocessing.common.CommonJavaPostProcessor
import com.robohorse.robopojogenerator.generator.postrocessing.common.KotlinDataClassPostProcessor
import com.robohorse.robopojogenerator.models.GenerationModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class PostProcessorFactoryTest {
    @RelaxedMockK
    lateinit var kotlinDataClassPostProcessor: KotlinDataClassPostProcessor

    @RelaxedMockK
    lateinit var autoValueClassPostProcessor: AutoValueClassPostProcessor

    @RelaxedMockK
    lateinit var commonJavaPostProcessor: CommonJavaPostProcessor

    @RelaxedMockK
    lateinit var generationModel: GenerationModel

    @InjectMockKs
    lateinit var factory: PostProcessorFactory

    @Test
    fun check_kotlinDataClassPostProcessor_creation() {
        every { generationModel.useKotlin }.returns(true)
        val result = factory.createPostProcessor(generationModel)
        assertEquals(result, kotlinDataClassPostProcessor)
    }

    @Test
    fun check_autoValueClassPostProcessor_creation() {
        every { generationModel.annotationEnum }.returns(AnnotationEnum.AUTO_VALUE_GSON)
        val result = factory.createPostProcessor(generationModel)
        assertEquals(result, autoValueClassPostProcessor)
    }

    @Test
    fun check_commonJavaPostProcessor_creation() {
        val result = factory.createPostProcessor(generationModel)
        assertEquals(result, commonJavaPostProcessor)
    }
}
