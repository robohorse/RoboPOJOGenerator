package com.robohorse.robopojogenerator.delegates.file

import com.robohorse.robopojogenerator.delegates.MessageDelegate
import com.robohorse.robopojogenerator.errors.FileWriteException
import com.robohorse.robopojogenerator.generator.consts.common.ClassItem
import com.robohorse.robopojogenerator.generator.postrocessing.PostProcessorFactory
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.models.ProjectModel
import java.io.File
import java.io.IOException

abstract class BaseWriterDelegate(
        private val messageDelegate: MessageDelegate,
        private val factory: PostProcessorFactory,
        private val fileWriterDelegate: FileWriterDelegate
) {

    abstract fun writeFiles(set: Set<ClassItem>,
                            generationModel: GenerationModel,
                            projectModel: ProjectModel)

    protected fun prepareClass(
            classItem: ClassItem,
            generationModel: GenerationModel
    ): String {
        val postProcessor = factory.createPostProcessor(generationModel)
        return postProcessor.proceed(classItem, generationModel)
    }

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
            fileWriterDelegate.writeToFile(classItemBody, file)
        } catch (e: IOException) {
            throw FileWriteException(e.message)
        }
    }
}

const val FILE_KOTLIN = ".kt"
const val FILE_JAVA = ".java"
