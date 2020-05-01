package com.robohorse.robopojogenerator.delegates.file

import com.robohorse.robopojogenerator.delegates.MessageDelegate
import com.robohorse.robopojogenerator.delegates.PreWriterDelegate
import com.robohorse.robopojogenerator.generator.consts.common.ClassItem
import com.robohorse.robopojogenerator.generator.postrocessing.PostProcessorFactory
import com.robohorse.robopojogenerator.generator.postrocessing.common.KotlinDataClassPostProcessor
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.models.ProjectModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class KotlinSingleFileWriterDelegateTest {
    @RelaxedMockK
    lateinit var classItem: ClassItem

    @RelaxedMockK
    lateinit var generationModel: GenerationModel

    @RelaxedMockK
    lateinit var projectModel: ProjectModel

    @RelaxedMockK
    lateinit var messageDelegate: MessageDelegate

    @RelaxedMockK
    lateinit var factory: PostProcessorFactory

    @RelaxedMockK
    lateinit var fileWriterDelegate: FileWriterDelegate

    @RelaxedMockK
    lateinit var kotlinDataClassPostProcessor: KotlinDataClassPostProcessor

    @RelaxedMockK
    lateinit var preWriterDelegate: PreWriterDelegate

    @InjectMockKs
    lateinit var delegateSingle: KotlinSingleFileWriterDelegate

    @Test
    fun check_writeFiles() {
        val set = setOf(classItem)
        every { generationModel.rootClassName }.returns(ROOT_CLASS_NAME)
        every { classItem.className }.returns(ROOT_CLASS_NAME)
        every { classItem.classImports }.returns(hashSetOf())
        every { projectModel.packageName }.returns(PACKAGE)
        every { kotlinDataClassPostProcessor.proceedClassImports(any(), generationModel) }.returns(StringBuilder())
        delegateSingle.writeFiles(set, generationModel, projectModel)
        verify { fileWriterDelegate.writeToFile(any(), any()) }
    }
}

private const val ROOT_CLASS_NAME = "Response"
private const val PACKAGE = "com.robohorse.test"
