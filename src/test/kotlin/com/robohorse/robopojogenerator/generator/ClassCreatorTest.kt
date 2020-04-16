package com.robohorse.robopojogenerator.generator

import com.robohorse.robopojogenerator.generator.consts.common.ClassCreator
import com.robohorse.robopojogenerator.generator.consts.common.ClassItem
import com.robohorse.robopojogenerator.generator.consts.common.FileWriteFactory
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.models.ProjectModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class ClassCreatorTest {

    @RelaxedMockK
    lateinit var roboPOJOGenerator: RoboPOJOGenerator

    @RelaxedMockK
    lateinit var fileWriteFactory: FileWriteFactory

    @RelaxedMockK
    lateinit var generationModel: GenerationModel

    @RelaxedMockK
    lateinit var projectModel: ProjectModel

    @InjectMockKs
    lateinit var classCreator: ClassCreator

    @Test
    fun generateFiles() {
        val classItem = ClassItem("")
        val classItemSet: MutableSet<ClassItem> = HashSet()
        classItemSet.add(classItem)
        every { roboPOJOGenerator.generate(generationModel) }.returns(classItemSet)
        classCreator.generateFiles(generationModel, projectModel)
        verify { fileWriteFactory.createFileWriter(generationModel, projectModel) }
    }
}
