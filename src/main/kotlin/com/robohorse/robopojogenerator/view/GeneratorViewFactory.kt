package com.robohorse.robopojogenerator.view

import com.intellij.openapi.ui.DialogBuilder
import com.robohorse.robopojogenerator.delegates.MessageDelegate
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.listeners.GenerateActionListener
import com.robohorse.robopojogenerator.listeners.GuiFormEventListener

class GeneratorViewFactory(
        private val messageDelegate: MessageDelegate,
        private val classGenerateHelper: ClassGenerateHelper,
        private val generatorViewBinder: GeneratorViewBinder,
        private val viewModelMapper: ViewModelMapper
) {

    fun bindView(builder: DialogBuilder, eventListener: GuiFormEventListener) {
        val generatorVew = GeneratorVew()
        val actionListener = GenerateActionListener(
                generatorVew = generatorVew,
                eventListener = eventListener,
                messageDelegate = messageDelegate,
                classGenerateHelper = classGenerateHelper,
                viewModelMapper = viewModelMapper
        )
        with(generatorVew) {
            generatorViewBinder.bindView(this)
            generateButton.addActionListener(actionListener)
            builder.setCenterPanel(rootView)
        }
        builder.apply {
            setTitle(PLUGIN_TITLE)
            removeAllActions()
            show()
        }
    }
}

private const val PLUGIN_TITLE = "RoboPOJOGenerator"
