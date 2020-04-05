package com.robohorse.robopojogenerator.controllers

import com.intellij.openapi.actionSystem.AnActionEvent
import com.robohorse.robopojogenerator.delegates.EnvironmentDelegate
import com.robohorse.robopojogenerator.delegates.GenerationDelegate
import com.robohorse.robopojogenerator.delegates.MessageDelegate
import com.robohorse.robopojogenerator.errors.RoboPluginException
import com.robohorse.robopojogenerator.models.ProjectModel
import com.robohorse.robopojogenerator.view.GeneratorViewBinder
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GeneratePOJOActionControllerTest {
    @RelaxedMockK
    lateinit var environmentDelegate: EnvironmentDelegate

    @RelaxedMockK
    lateinit var messageDelegate: MessageDelegate

    @RelaxedMockK
    lateinit var generatorViewBinder: GeneratorViewBinder

    @RelaxedMockK
    lateinit var generationDelegate: GenerationDelegate

    @RelaxedMockK
    lateinit var projectModel: ProjectModel

    @RelaxedMockK
    lateinit var event: AnActionEvent

    @InjectMockKs
    lateinit var generatePOJOActionController: GeneratePOJOActionController

    @Test
    fun onActionHandled() {
        every { environmentDelegate.obtainProjectModel(event) }.returns(projectModel)
        generatePOJOActionController.onActionHandled(event)
        verify { generatorViewBinder.bindView(any(), any()) }
    }

    @Test
    fun onActionHandled_withError() {
        val exception = RoboPluginException("", "")
        every { environmentDelegate.obtainProjectModel(event) }.throws(exception)
        generatePOJOActionController.onActionHandled(event)
        verify { messageDelegate.onPluginExceptionHandled(exception) }
    }
}
