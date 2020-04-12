package com.robohorse.robopojogenerator

import com.intellij.openapi.actionSystem.AnActionEvent
import com.robohorse.robopojogenerator.controllers.GeneratePOJOActionController
import com.robohorse.robopojogenerator.di.appModule
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject

class PluginApplication : KoinComponent {
    init {
        startKoin { modules(appModule) }
    }

    private val controller: GeneratePOJOActionController by inject()

    fun actionPerformed(actionEvent: AnActionEvent) {
        controller.onActionHandled(actionEvent)
    }
}
