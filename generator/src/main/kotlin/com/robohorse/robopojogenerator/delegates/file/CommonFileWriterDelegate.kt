package com.robohorse.robopojogenerator.delegates.file

import com.robohorse.robopojogenerator.delegates.MessageDelegate
import com.robohorse.robopojogenerator.delegates.PreWriterDelegate
import com.robohorse.robopojogenerator.generator.common.common.ClassItem
import com.robohorse.robopojogenerator.generator.postrocessing.PostProcessorFactory
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.models.ProjectModel

internal class CommonFileWriterDelegate(
    messageDelegate: MessageDelegate,
    factory: PostProcessorFactory,
    fileWriterDelegate: FileWriterDelegate,
    preWriterDelegate: PreWriterDelegate
) : BaseWriterDelegate(messageDelegate, factory, fileWriterDelegate, preWriterDelegate) {

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
