package com.robohorse.robopojogenerator

import com.intellij.openapi.actionSystem.AnActionEvent
import com.robohorse.robopojogenerator.controllers.GeneratePOJOActionController
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

internal class PluginApplication : KoinComponent {
    init {
        GlobalContext.getOrNull() ?: startKoin {
            modules(appModule + generatorModule)
        }
    }

    private val controller: GeneratePOJOActionController by inject()

    fun actionPerformed(actionEvent: AnActionEvent) {
        controller.onActionHandled(actionEvent)
    }
}
