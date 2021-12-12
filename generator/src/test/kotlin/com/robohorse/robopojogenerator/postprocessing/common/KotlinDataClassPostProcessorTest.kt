package com.robohorse.robopojogenerator.postprocessing.common

import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.postrocessing.common.KotlinDataClassPostProcessor
import com.robohorse.robopojogenerator.properties.ClassItem
import com.robohorse.robopojogenerator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.utils.ClassTemplateHelper
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlin.test.BeforeTest
import kotlin.test.Test

internal class KotlinDataClassPostProcessorTest {

    @RelaxedMockK
    lateinit var classTemplateHelper: ClassTemplateHelper

    @RelaxedMockK
    lateinit var generateHelper: ClassGenerateHelper

    @RelaxedMockK
    lateinit var classItem: ClassItem

    @RelaxedMockK
    lateinit var generationModel: GenerationModel

    @InjectMockKs
    lateinit var processor: KotlinDataClassPostProcessor

    @BeforeTest
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    fun check_createClassTemplate() {
        val classBody = "Class"
        processor.createClassTemplate(classItem, classBody, generationModel)
        verify { classTemplateHelper.createClassBodyKotlinDataClass(classItem, classBody) }
    }
}
