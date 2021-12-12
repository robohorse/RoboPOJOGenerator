package com.robohorse.robopojogenerator.processing

import com.robohorse.robopojogenerator.RoboPOJOGenerator
import com.robohorse.robopojogenerator.filewriter.FileDelegateFactory
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.models.ProjectModel
import com.robohorse.robopojogenerator.properties.ClassItem
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Test
import kotlin.test.BeforeTest

internal class ClassCreatorTest {

    @RelaxedMockK
    lateinit var roboPOJOGenerator: RoboPOJOGenerator

    @RelaxedMockK
    lateinit var fileWriteFactory: FileDelegateFactory

    @RelaxedMockK
    lateinit var generationModel: GenerationModel

    @RelaxedMockK
    lateinit var projectModel: ProjectModel

    @InjectMockKs
    lateinit var classCreator: ClassCreator

    @BeforeTest
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    fun generateFiles() {
        val classItem = ClassItem("")
        val classItemSet: MutableSet<ClassItem> = HashSet()
        classItemSet.add(classItem)
        every { roboPOJOGenerator.generate(generationModel) }.returns(classItemSet)
        classCreator.generateFiles(generationModel, projectModel)
        verify { fileWriteFactory.createFileWriter(generationModel) }
    }
}
