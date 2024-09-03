package com.robohorse.robopojogenerator.controllers

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.DialogBuilder
import com.robohorse.robopojogenerator.GenerationDelegate
import com.robohorse.robopojogenerator.delegates.EnvironmentDelegate

import com.robohorse.robopojogenerator.delegates.MessageDelegate
import com.robohorse.robopojogenerator.errors.RoboPluginException
import com.robohorse.robopojogenerator.listeners.GuiFormEventListener
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.persistense.ViewStateService
import com.robohorse.robopojogenerator.view.GeneratorViewFactory

internal class GeneratePOJOActionController(
    private val environmentDelegate: EnvironmentDelegate,
    private val messageDelegate: MessageDelegate,
    private val generatorViewFactory: GeneratorViewFactory,
    private val generationDelegate: GenerationDelegate,
    private val viewStateService: ViewStateService
) {

    fun onActionHandled(event: AnActionEvent) = try {
        proceed(event)
    } catch (exception: RoboPluginException) {
        messageDelegate.onPluginExceptionHandled(exception)
    }

    private fun proceed(event: AnActionEvent) {
        val projectModel = environmentDelegate.obtainProjectModel(event)
        val dialogBuilder = DialogBuilder()
        val window = dialogBuilder.window
        generatorViewFactory.bindView(
            dialogBuilder,
            object : GuiFormEventListener {
                override fun onJsonDataObtained(model: GenerationModel) {
                    viewStateService.persistModel(model)
                    window.dispose()
                    generationDelegate.runGenerationTask(model, projectModel)
                }
            }
        )
    }
}
