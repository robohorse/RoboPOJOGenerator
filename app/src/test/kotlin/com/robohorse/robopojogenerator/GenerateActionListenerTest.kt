package com.robohorse.robopojogenerator

import com.robohorse.robopojogenerator.delegates.MessageDelegate
import com.robohorse.robopojogenerator.errors.RoboPluginException
import com.robohorse.robopojogenerator.form.GeneratorVew
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.listeners.GenerateActionListener
import com.robohorse.robopojogenerator.listeners.GuiFormEventListener
import com.robohorse.robopojogenerator.view.ViewModelMapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import java.awt.event.ActionEvent
import kotlin.test.BeforeTest
import kotlin.test.Test

internal class GenerateActionListenerTest {
    @RelaxedMockK
    lateinit var generatorVew: GeneratorVew

    @RelaxedMockK
    lateinit var eventListener: GuiFormEventListener

    @RelaxedMockK
    lateinit var actionEvent: ActionEvent

    @RelaxedMockK
    lateinit var messageDelegate: MessageDelegate

    @RelaxedMockK
    lateinit var classGenerateHelper: ClassGenerateHelper

    @RelaxedMockK
    lateinit var viewModelMapper: ViewModelMapper

    @RelaxedMockK
    lateinit var exception: RoboPluginException

    @InjectMockKs
    lateinit var listener: GenerateActionListener

    @BeforeTest
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    fun onActionHandled_withError() {
        every { classGenerateHelper.validateClassName(any()) } throws exception
        listener.actionPerformed(actionEvent)
        verify { messageDelegate.onPluginExceptionHandled(exception) }
    }
}
