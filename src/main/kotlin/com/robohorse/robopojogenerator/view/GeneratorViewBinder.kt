package com.robohorse.robopojogenerator.view

import com.intellij.openapi.ui.DialogBuilder
import com.robohorse.robopojogenerator.delegates.MessageDelegate
import com.robohorse.robopojogenerator.generator.consts.annotations.AnnotationEnum
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.listeners.AutoValueSelectionListener
import com.robohorse.robopojogenerator.listeners.GenerateActionListener
import com.robohorse.robopojogenerator.listeners.GuiFormEventListener
import com.robohorse.robopojogenerator.listeners.KotlinCheckBoxStateListener
import javax.swing.ButtonGroup

class GeneratorViewBinder(
        private val messageDelegate: MessageDelegate,
        private val classGenerateHelper: ClassGenerateHelper
) {

    fun bindView(builder: DialogBuilder, eventListener: GuiFormEventListener) {
        val generatorVew = GeneratorVew()
        val actionListener = GenerateActionListener(
                generatorVew,
                eventListener,
                messageDelegate,
                classGenerateHelper
        )
        with(generatorVew) {
            generateButton.addActionListener(actionListener)
            kotlinCheckBox.addItemListener(KotlinCheckBoxStateListener(generatorVew))
            bindGroupViews(typeButtonGroup, generatorVew)
            builder.setCenterPanel(rootView)
        }
        builder.setTitle("RoboPOJOGenerator")
        builder.removeAllActions()
        builder.show()
    }

    private fun bindGroupViews(buttonGroup: ButtonGroup, generatorVew: GeneratorVew) {
        val buttons = buttonGroup.elements
        for (annotationItems in AnnotationEnum.values()) {
            if (buttons.hasMoreElements()) {
                val button = buttons.nextElement()
                button.text = annotationItems.text
                if (annotationItems == AnnotationEnum.AUTO_VALUE_GSON) {
                    button.addItemListener(AutoValueSelectionListener(generatorVew))
                }
            } else {
                break
            }
        }
    }
}
