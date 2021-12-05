package com.robohorse.robopojogenerator.controllers

import com.intellij.openapi.actionSystem.AnActionEvent
import com.robohorse.robopojogenerator.delegates.EnvironmentDelegate
import com.robohorse.robopojogenerator.delegates.GenerationDelegate
import com.robohorse.robopojogenerator.delegates.MessageDelegate
import com.robohorse.robopojogenerator.errors.RoboPluginException
import com.robohorse.robopojogenerator.models.ProjectModel
import com.robohorse.robopojogenerator.view.GeneratorViewFactory
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlin.test.BeforeTest
import kotlin.test.Test

class GeneratePOJOActionControllerTest {
    @RelaxedMockK
    lateinit var environmentDelegate: EnvironmentDelegate

    @RelaxedMockK
    lateinit var messageDelegate: MessageDelegate

    @RelaxedMockK
    lateinit var generatorViewFactory: GeneratorViewFactory

    @RelaxedMockK
    lateinit var generationDelegate: GenerationDelegate

    @RelaxedMockK
    lateinit var projectModel: ProjectModel

    @RelaxedMockK
    lateinit var event: AnActionEvent

    @InjectMockKs
    lateinit var generatePOJOActionController: GeneratePOJOActionController

    @BeforeTest
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    fun onActionHandled_withError() {
        val exception = RoboPluginException("", "")
        every { environmentDelegate.obtainProjectModel(event) }.throws(exception)
        generatePOJOActionController.onActionHandled(event)
        verify { messageDelegate.showSuccessMessage() }
    }
}
