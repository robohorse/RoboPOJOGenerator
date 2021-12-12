package com.robohorse.robopojogenerator.filewriter

import com.robohorse.robopojogenerator.delegates.MessageDelegate
import com.robohorse.robopojogenerator.delegates.PreWriterDelegate
import com.robohorse.robopojogenerator.errors.FileWriteException
import com.robohorse.robopojogenerator.properties.ClassItem
import com.robohorse.robopojogenerator.postrocessing.PostProcessorFactory
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.models.ProjectModel
import java.io.File
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
        val path = projectModel.directory.virtualFile.path
        val fileName = "$className${if (generationModel.useKotlin) FILE_KOTLIN else FILE_JAVA}"
        val file = File(path + File.separator + fileName)
        try {
            if (file.exists()) {
                if (generationModel.rewriteClasses) {
                    file.delete()
                    messageDelegate.logEventMessage("updated $fileName")
                } else {
                    messageDelegate.logEventMessage("skipped $fileName")
                }
            } else {
                messageDelegate.logEventMessage("created $fileName")
            }
            fileWriterDelegate.writeToFile(
                preWriterDelegate.updateFileBody(generationModel, classItemBody), file
            )
        } catch (e: IOException) {
            throw FileWriteException(e.message)
        }
    }
}

const val FILE_KOTLIN = ".kt"
const val FILE_JAVA = ".java"
