package com.robohorse.robopojogenerator.generator.consts.common

import com.robohorse.robopojogenerator.delegates.FileWriterDelegate
import com.robohorse.robopojogenerator.generator.RoboPOJOGenerator
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.models.ProjectModel

class ClassCreator(
        private val roboPOJOGenerator: RoboPOJOGenerator,
        private val fileWriterDelegate: FileWriterDelegate
) {

    fun generateFiles(
            generationModel: GenerationModel,
            projectModel: ProjectModel
    ) = roboPOJOGenerator.generate(generationModel).forEach {
        fileWriterDelegate.writeFile(it, generationModel, projectModel)
    }
}
