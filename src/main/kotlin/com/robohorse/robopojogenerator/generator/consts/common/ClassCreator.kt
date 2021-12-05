package com.robohorse.robopojogenerator.generator.consts.common

import com.robohorse.robopojogenerator.generator.RoboPOJOGenerator
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.models.ProjectModel

internal class ClassCreator(
    private val roboPOJOGenerator: RoboPOJOGenerator,
    private val fileWriteFactory: FileWriteFactory
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
