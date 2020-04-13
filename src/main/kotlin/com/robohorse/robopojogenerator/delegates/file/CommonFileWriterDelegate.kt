package com.robohorse.robopojogenerator.delegates.file

import com.robohorse.robopojogenerator.delegates.MessageDelegate
import com.robohorse.robopojogenerator.generator.consts.common.ClassItem
import com.robohorse.robopojogenerator.generator.postrocessing.PostProcessorFactory
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.models.ProjectModel

class CommonFileWriterDelegate(
        messageDelegate: MessageDelegate,
        factory: PostProcessorFactory,
        fileWriterDelegate: FileWriterDelegate
) : BaseWriterDelegate(messageDelegate, factory, fileWriterDelegate) {

    override fun writeFiles(
            set: Set<ClassItem>,
            generationModel: GenerationModel,
            projectModel: ProjectModel
    ) {
        set.forEach {
            it.className?.let { className ->
                writeFile(
                        classItemBody = prepareClass(it.apply {
                            packagePath = projectModel.packageName
                        }, generationModel),
                        className = className,
                        generationModel = generationModel,
                        projectModel = projectModel
                )
            }
        }
    }
}
