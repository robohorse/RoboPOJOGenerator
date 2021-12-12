package com.robohorse.robopojogenerator.filewriter

import com.robohorse.robopojogenerator.filewriter.common.CommonFileWriterDelegate
import com.robohorse.robopojogenerator.filewriter.common.KotlinSingleFileWriterDelegate
import com.robohorse.robopojogenerator.models.GenerationModel

internal class FileDelegateFactory(
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
