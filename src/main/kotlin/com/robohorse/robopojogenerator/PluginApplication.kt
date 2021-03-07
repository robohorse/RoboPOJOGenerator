package com.robohorse.robopojogenerator

import com.intellij.openapi.actionSystem.AnActionEvent
import com.robohorse.robopojogenerator.controllers.GeneratePOJOActionController
import com.robohorse.robopojogenerator.di.appModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

class PluginApplication : KoinComponent {
    init {
        startKoin { modules(appModule) }
    }

    private val controller: GeneratePOJOActionController by inject()

    fun actionPerformed(actionEvent: AnActionEvent) {
        controller.onActionHandled(actionEvent)
    }
}
