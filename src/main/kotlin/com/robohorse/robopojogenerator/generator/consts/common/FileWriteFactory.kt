package com.robohorse.robopojogenerator.generator.consts.common

import com.robohorse.robopojogenerator.delegates.file.BaseWriterDelegate
import com.robohorse.robopojogenerator.delegates.file.CommonFileWriterDelegate
import com.robohorse.robopojogenerator.delegates.file.KotlinFileWriterDelegate
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.models.ProjectModel

class FileWriteFactory(
        private val commonFileWriterDelegate: CommonFileWriterDelegate,
        private val kotlinFileWriterDelegate: KotlinFileWriterDelegate
) {

    fun createFileWriter(generationModel: GenerationModel,
                         projectModel: ProjectModel
    ): BaseWriterDelegate =
            if (generationModel.useKotlin) {
                kotlinFileWriterDelegate
            } else {
                commonFileWriterDelegate
            }
}
