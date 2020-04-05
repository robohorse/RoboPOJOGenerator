package com.robohorse.robopojogenerator.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.robohorse.robopojogenerator.controllers.GeneratePOJOActionController
import com.robohorse.robopojogenerator.di.appModule
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject

class GeneratePOJOAction : AnAction(), KoinComponent {
    init {
        startKoin { modules(appModule) }
    }
    private val controller: GeneratePOJOActionController by inject()

    override fun actionPerformed(actionEvent: AnActionEvent) {
        controller.onActionHandled(actionEvent)
    }
}
