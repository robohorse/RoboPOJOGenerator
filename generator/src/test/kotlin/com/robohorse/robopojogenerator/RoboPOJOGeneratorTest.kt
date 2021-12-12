package com.robohorse.robopojogenerator

import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.processing.ClassProcessor
import com.robohorse.robopojogenerator.properties.JsonModel.JsonItem
import com.robohorse.robopojogenerator.utils.ProcessingModelResolver
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlin.test.BeforeTest
import kotlin.test.Test

internal class RoboPOJOGeneratorTest {
    @RelaxedMockK
    lateinit var processor: ClassProcessor

    @RelaxedMockK
    lateinit var model: GenerationModel

    @RelaxedMockK
    lateinit var processingModelResolver: ProcessingModelResolver

    @RelaxedMockK
    lateinit var jsonItem: JsonItem

    @InjectMockKs
    lateinit var generator: RoboPOJOGenerator

    @BeforeTest
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    fun check_generation() {
        val content = "{}"
        val className = "Main.kt"
        every { processingModelResolver.resolveJsonModel(model) } returns jsonItem
        every { model.content }.returns(content)
        every { model.rootClassName }.returns(className)
        generator.generate(model)
        verify { processor.proceed(any(), any()) }
    }
}
