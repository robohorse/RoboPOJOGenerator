package com.robohorse.robopojogenerator.delegates

import com.robohorse.robopojogenerator.errors.FileWriteException
import com.robohorse.robopojogenerator.generator.consts.common.ClassItem
import com.robohorse.robopojogenerator.generator.postrocessing.PostProcessorFactory
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.models.ProjectModel
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException

class FileWriterDelegate(
        private val messageDelegate: MessageDelegate,
        private val factory: PostProcessorFactory
) {

    fun writeFile(
            classItem: ClassItem,
            generationModel: GenerationModel,
            projectModel: ProjectModel
    ) {
        val path = projectModel.directory.virtualFile.path
        val fileName = "${classItem.className}${if (generationModel.useKotlin) FILE_KOTLIN else FILE_JAVA}"
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
            if (!file.exists()) {
                file.createNewFile()
                writeToFile(classItem, generationModel, projectModel, file)
            }
        } catch (e: IOException) {
            throw FileWriteException(e.message)
        }
    }

    private fun writeToFile(
            classItem: ClassItem,
            generationModel: GenerationModel,
            projectModel: ProjectModel,
            file: File
    ) = FileUtils.writeStringToFile(
            file,
            prepareClass(classItem, generationModel, projectModel),
            ENCODING
    )

    private fun prepareClass(
            classItem: ClassItem,
            generationModel: GenerationModel,
            projectModel: ProjectModel
    ): String {
        classItem.packagePath = projectModel.packageName
        val postProcessor = factory.createPostProcessor(generationModel)
        return postProcessor.proceed(classItem, generationModel)
    }
}

private const val ENCODING = "UTF-8"
private const val FILE_KOTLIN = ".kt"
private const val FILE_JAVA = ".java"
