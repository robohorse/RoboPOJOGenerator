package com.robohorse.robopojogenerator.generator.postprocessing.common

import com.robohorse.robopojogenerator.generator.consts.common.ClassItem
import com.robohorse.robopojogenerator.generator.postrocessing.common.AutoValueClassPostProcessor
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.generator.utils.ClassTemplateHelper
import com.robohorse.robopojogenerator.models.GenerationModel
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class AutoValueClassPostProcessorTest {
    @RelaxedMockK
    lateinit var generateHelper: ClassGenerateHelper

    @RelaxedMockK
    lateinit var classTemplateHelper: ClassTemplateHelper

    @RelaxedMockK
    lateinit var classItem: ClassItem

    @RelaxedMockK
    lateinit var generationModel: GenerationModel

    @InjectMockKs
    lateinit var processor: AutoValueClassPostProcessor

    @Test
    fun check_createClassTemplate() {
        val classBody = "Class"
        processor.createClassTemplate(classItem, classBody, generationModel)
        verify { classTemplateHelper.createClassBodyAbstract(classItem, classBody) }
    }
}
