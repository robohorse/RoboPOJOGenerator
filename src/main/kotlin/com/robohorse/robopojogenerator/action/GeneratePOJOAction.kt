package com.robohorse.robopojogenerator.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.robohorse.robopojogenerator.PluginApplication

internal class GeneratePOJOAction : AnAction() {
    private val pluginApplication = PluginApplication()

    override fun actionPerformed(event: AnActionEvent) = pluginApplication.actionPerformed(event)
}
