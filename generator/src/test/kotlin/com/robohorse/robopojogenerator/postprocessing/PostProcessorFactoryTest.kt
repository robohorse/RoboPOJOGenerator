package com.robohorse.robopojogenerator.postprocessing

import com.robohorse.robopojogenerator.models.FrameworkVW
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.postrocessing.PostProcessorFactory
import com.robohorse.robopojogenerator.postrocessing.common.AutoValueClassPostProcessor
import com.robohorse.robopojogenerator.postrocessing.common.CommonJavaPostProcessor
import com.robohorse.robopojogenerator.postrocessing.common.JavaRecordsPostProcessor
import com.robohorse.robopojogenerator.postrocessing.common.KotlinDataClassPostProcessor
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class PostProcessorFactoryTest {
    @RelaxedMockK
    lateinit var kotlinDataClassPostProcessor: KotlinDataClassPostProcessor

    @RelaxedMockK
    lateinit var autoValueClassPostProcessor: AutoValueClassPostProcessor

    @RelaxedMockK
    lateinit var commonJavaPostProcessor: CommonJavaPostProcessor

    @RelaxedMockK
    lateinit var generationModel: GenerationModel

    @RelaxedMockK
    lateinit var autoValueAnnotation: FrameworkVW.AutoValue

    @RelaxedMockK
    lateinit var noneJavaAnnotation: FrameworkVW.None

    @RelaxedMockK
    lateinit var noneJavaRecordsAnnotation: FrameworkVW.NoneJavaRecords

    @RelaxedMockK
    lateinit var javaRecordsPostProcessor: JavaRecordsPostProcessor

    @InjectMockKs
    lateinit var factory: PostProcessorFactory

    @BeforeTest
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    fun check_kotlinDataClassPostProcessor_creation() {
        every { generationModel.useKotlin }.returns(true)
        val result = factory.createPostProcessor(generationModel)
        assertEquals(result, kotlinDataClassPostProcessor)
    }
}
