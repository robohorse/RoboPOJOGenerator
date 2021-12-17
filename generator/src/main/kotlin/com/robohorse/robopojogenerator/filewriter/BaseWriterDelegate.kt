package com.robohorse.robopojogenerator.filewriter

import com.robohorse.robopojogenerator.delegates.MessageDelegate
import com.robohorse.robopojogenerator.delegates.PreWriterDelegate
import com.robohorse.robopojogenerator.errors.FileWriteException
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.models.ProjectModel
import com.robohorse.robopojogenerator.postrocessing.PostProcessorFactory
import com.robohorse.robopojogenerator.properties.ClassItem
import java.io.IOException

internal abstract class BaseWriterDelegate(
    private val messageDelegate: MessageDelegate,
    private val factory: PostProcessorFactory,
    private val fileWriterDelegate: FileWriter,
    private val preWriterDelegate: PreWriterDelegate
) {

    abstract fun writeFiles(
        set: Set<ClassItem>,
        generationModel: GenerationModel,
        projectModel: ProjectModel
    )

    protected fun prepareClass(
        classItem: ClassItem,
        generationModel: GenerationModel
    ) = factory.createPostProcessor(generationModel)
        .proceed(classItem, generationModel)

    protected fun writeFile(
        classItemBody: String,
        className: String,
        generationModel: GenerationModel,
        projectModel: ProjectModel
    ) {
        val fileName = "$className${if (generationModel.useKotlin) FILE_KOTLIN else FILE_JAVA}"
        try {
            if (projectModel.directory.findFile(fileName) != null) {
                if (generationModel.rewriteClasses) {
                    messageDelegate.logEventMessage("updated $fileName")
                } else {
                    messageDelegate.logEventMessage("skipped $fileName")
                }
            } else {
                messageDelegate.logEventMessage("created $fileName")
            }
            fileWriterDelegate.writeToFile(
                preWriterDelegate.updateFileBody(generationModel, classItemBody), fileName,
                projectModel,
                generationModel
            )
        } catch (e: IOException) {
            throw FileWriteException(e.message)
        }
    }
}

const val FILE_KOTLIN = ".kt"
const val FILE_JAVA = ".java"
