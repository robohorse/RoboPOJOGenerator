package com.robohorse.robopojogenerator

import com.intellij.openapi.actionSystem.AnActionEvent
import com.robohorse.robopojogenerator.controllers.GeneratePOJOActionController
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

object PluginApplication : KoinComponent {
    init {
        GlobalContext.getOrNull() ?: startKoin {
            modules(
                appModule + generatorModule + coreModule
            )
        }
    }

    private val controller: GeneratePOJOActionController by inject()

    fun actionPerformed(actionEvent: AnActionEvent) {
        controller.onActionHandled(actionEvent)
    }
}
