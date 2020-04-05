package com.robohorse.robopojogenerator.listeners

import com.robohorse.robopojogenerator.generator.consts.annotations.AnnotationEnum
import com.robohorse.robopojogenerator.view.GeneratorVew
import java.awt.event.ItemEvent
import java.awt.event.ItemListener

class KotlinCheckBoxStateListener(
        private val generatorVew: GeneratorVew
) : ItemListener {

    override fun itemStateChanged(itemEvent: ItemEvent) {
        val kotlinEnabled = itemEvent.stateChange == ItemEvent.SELECTED
        enableRadioButton(generatorVew, !kotlinEnabled)
        enableCheckBoxes(generatorVew, kotlinEnabled)
    }

    private fun enableCheckBoxes(
            generatorVew: GeneratorVew, kotlinEnabled: Boolean
    ) = generatorVew.apply {
        useGettersCheckBox.isEnabled = !kotlinEnabled
        useSettersCheckBox.isEnabled = !kotlinEnabled
        useStringCheckBox.isEnabled = !kotlinEnabled
    }

    private fun enableRadioButton(generatorVew: GeneratorVew, enable: Boolean) {
        val buttonGroup = generatorVew.typeButtonGroup
        val buttons = buttonGroup.elements
        while (buttons.hasMoreElements()) {
            val button = buttons.nextElement()
            if (AnnotationEnum.AUTO_VALUE_GSON.text == button.text) {
                button.isEnabled = enable
            } else if (AnnotationEnum.NONE.text == button.text) {
                button.isSelected = true
            }
        }
    }
}
