package com.robohorse.robopojogenerator

import com.robohorse.robopojogenerator.filewriter.FileDelegateFactory
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.models.ProjectModel

internal class ClassCreator(
    private val roboPOJOGenerator: RoboPOJOGenerator,
    private val fileWriteFactory: FileDelegateFactory
) {

    fun generateFiles(
        generationModel: GenerationModel,
        projectModel: ProjectModel
    ) = fileWriteFactory.createFileWriter(
        generationModel
    ).writeFiles(
        roboPOJOGenerator.generate(generationModel),
        generationModel,
        projectModel
    )
}
