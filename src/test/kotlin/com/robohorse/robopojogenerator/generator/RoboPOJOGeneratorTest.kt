package com.robohorse.robopojogenerator.generator

import com.robohorse.robopojogenerator.generator.consts.common.JsonModel.JsonItem
import com.robohorse.robopojogenerator.generator.processing.ClassProcessor
import com.robohorse.robopojogenerator.generator.utils.ProcessingModelManager
import com.robohorse.robopojogenerator.models.GenerationModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class RoboPOJOGeneratorTest {
    @RelaxedMockK
    lateinit var processor: ClassProcessor

    @RelaxedMockK
    lateinit var model: GenerationModel

    @RelaxedMockK
    lateinit var processingModelManager: ProcessingModelManager

    @RelaxedMockK
    lateinit var jsonItem: JsonItem

    @InjectMockKs
    lateinit var generator: RoboPOJOGenerator

    @Test
    fun check_generation() {
        val content = "{}"
        val className = "Main.kt"
        every { processingModelManager.resolveJsonModel(model) } returns jsonItem
        every { model.content }.returns(content)
        every { model.rootClassName }.returns(className)
        generator.generate(model)
        verify { processor.proceed(any(), any()) }
    }
}
