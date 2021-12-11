package com.robohorse.robopojogenerator.listeners

import com.robohorse.robopojogenerator.delegates.MessageDelegate
import com.robohorse.robopojogenerator.errors.RoboPluginException
import com.robohorse.robopojogenerator.form.GeneratorVew
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.view.ViewModelMapper
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

internal class GenerateActionListener(
    private val generatorVew: GeneratorVew,
    private val eventListener: GuiFormEventListener,
    private val messageDelegate: MessageDelegate,
    private val classGenerateHelper: ClassGenerateHelper,
    private val viewModelMapper: ViewModelMapper
) : ActionListener {

    override fun actionPerformed(actionEvent: ActionEvent) {
        with(generatorVew) {
            try {
                classGenerateHelper.validateClassName(className.text)
                classGenerateHelper.validateJsonContent(textArea.text ?: "")
                eventListener.onJsonDataObtained(viewModelMapper.map(generatorVew))
            } catch (exception: RoboPluginException) {
                messageDelegate.onPluginExceptionHandled(exception)
            }
        }
    }
}
