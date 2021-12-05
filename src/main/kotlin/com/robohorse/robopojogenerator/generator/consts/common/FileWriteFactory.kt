package com.robohorse.robopojogenerator.generator.consts.common

import com.robohorse.robopojogenerator.delegates.file.BaseWriterDelegate
import com.robohorse.robopojogenerator.delegates.file.CommonFileWriterDelegate
import com.robohorse.robopojogenerator.delegates.file.KotlinSingleFileWriterDelegate
import com.robohorse.robopojogenerator.models.GenerationModel

internal class FileWriteFactory(
    private val commonFileWriterDelegate: CommonFileWriterDelegate,
    private val kotlinSingleFileWriterDelegate: KotlinSingleFileWriterDelegate
) {

    fun createFileWriter(generationModel: GenerationModel): BaseWriterDelegate =
        if (generationModel.useKotlin && generationModel.useKotlinSingleDataClass) {
            kotlinSingleFileWriterDelegate
        } else {
            commonFileWriterDelegate
        }
}
