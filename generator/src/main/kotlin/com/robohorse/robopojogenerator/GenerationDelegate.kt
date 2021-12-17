package com.robohorse.robopojogenerator

import com.robohorse.robopojogenerator.delegates.EnvironmentDelegate
import com.robohorse.robopojogenerator.delegates.MessageDelegate
import com.robohorse.robopojogenerator.errors.RoboPluginException
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.models.ProjectModel

interface GenerationDelegate {
    fun runGenerationTask(
        generationModel: GenerationModel,
        projectModel: ProjectModel
    )
}

internal class GenerationDelegateImpl(
    private val classCreator: ClassCreator,
    private val environmentDelegate: EnvironmentDelegate,
    private val messageDelegate: MessageDelegate
) : GenerationDelegate {

    override fun runGenerationTask(
        generationModel: GenerationModel,
        projectModel: ProjectModel
    ) {
        try {
            classCreator.generateFiles(generationModel, projectModel)
            messageDelegate.showSuccessMessage()
        } catch (e: RoboPluginException) {
            messageDelegate.onPluginExceptionHandled(e)
        } finally {
            environmentDelegate.refreshProject(projectModel)
        }
    }
}
