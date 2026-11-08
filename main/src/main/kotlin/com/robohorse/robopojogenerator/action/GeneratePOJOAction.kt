package com.robohorse.robopojogenerator.action

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.robohorse.robopojogenerator.PluginApplication

class GeneratePOJOAction : AnAction() {
    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

    override fun actionPerformed(
        event: AnActionEvent
    ) = PluginApplication.actionPerformed(event)
}
