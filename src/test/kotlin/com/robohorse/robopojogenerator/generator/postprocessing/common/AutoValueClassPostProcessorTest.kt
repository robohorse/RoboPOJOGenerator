package com.robohorse.robopojogenerator.generator.postprocessing.common

import com.robohorse.robopojogenerator.generator.consts.common.ClassItem
import com.robohorse.robopojogenerator.generator.postrocessing.common.AutoValueClassPostProcessor
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.generator.utils.ClassTemplateHelper
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

    @InjectMockKs
    lateinit var processor: AutoValueClassPostProcessor

    @Test
    fun check_createClassTemplate() {
        val classBody = "Class"
        processor.createClassTemplate(classItem, classBody)
        verify { classTemplateHelper.createClassBodyAbstract(classItem, classBody) }
    }
}
