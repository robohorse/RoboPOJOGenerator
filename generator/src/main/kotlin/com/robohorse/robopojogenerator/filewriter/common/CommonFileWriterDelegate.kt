package com.robohorse.robopojogenerator.filewriter.common

import com.robohorse.robopojogenerator.delegates.MessageDelegate
import com.robohorse.robopojogenerator.delegates.PreWriterDelegate
import com.robohorse.robopojogenerator.filewriter.BaseWriterDelegate
import com.robohorse.robopojogenerator.filewriter.FileWriter
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.models.ProjectModel
import com.robohorse.robopojogenerator.postrocessing.PostProcessorFactory
import com.robohorse.robopojogenerator.properties.ClassItem

internal class CommonFileWriterDelegate(
    messageDelegate: MessageDelegate,
    factory: PostProcessorFactory,
    fileWriterDelegate: FileWriter,
    preWriterDelegate: PreWriterDelegate
) : BaseWriterDelegate(
    messageDelegate,
    factory,
    fileWriterDelegate,
    preWriterDelegate
) {

    override fun writeFiles(
        set: Set<ClassItem>,
        generationModel: GenerationModel,
        projectModel: ProjectModel
    ) = set.forEach { classItem ->
        classItem.className?.let { className ->
            writeFile(
                classItemBody = prepareClass(
                    classItem.apply {
                        packagePath = projectModel.packageName
                    },
                    generationModel
                ),
                className = className,
                generationModel = generationModel,
                projectModel = projectModel
            )
        }
    }
}
