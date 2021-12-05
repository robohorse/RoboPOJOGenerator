package com.robohorse.robopojogenerator.generator.postprocessing.common

import com.robohorse.robopojogenerator.generator.consts.common.ClassItem
import com.robohorse.robopojogenerator.generator.postrocessing.common.CommonJavaPostProcessor
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.generator.utils.ClassTemplateHelper
import com.robohorse.robopojogenerator.models.GenerationModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlin.test.BeforeTest
import kotlin.test.Test

class CommonJavaPostProcessorTest {
    @RelaxedMockK
    lateinit var generateHelper: ClassGenerateHelper

    @RelaxedMockK
    lateinit var classTemplateHelper: ClassTemplateHelper

    @RelaxedMockK
    lateinit var classItem: ClassItem

    @RelaxedMockK
    lateinit var generationModel: GenerationModel

    @InjectMockKs
    lateinit var processor: CommonJavaPostProcessor

    @BeforeTest
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    fun check_createClassTemplate() {
        val classBody = "Class"
        processor.createClassTemplate(classItem, classBody, generationModel)
        verify { classTemplateHelper.createClassBody(classItem, classBody) }
    }
}
