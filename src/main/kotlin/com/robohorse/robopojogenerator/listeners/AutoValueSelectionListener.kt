package com.robohorse.robopojogenerator.listeners

import com.robohorse.robopojogenerator.view.GeneratorVew
import java.awt.event.ItemEvent
import java.awt.event.ItemListener

class AutoValueSelectionListener(
        private val generatorVew: GeneratorVew
) : ItemListener {

    override fun itemStateChanged(itemEvent: ItemEvent) {
        val autoValueEnabled = itemEvent.stateChange == ItemEvent.SELECTED
        generatorVew.apply {
            useGettersCheckBox.isEnabled = !autoValueEnabled
            useSettersCheckBox.isEnabled = !autoValueEnabled
            useStringCheckBox.isEnabled = !autoValueEnabled
            kotlinCheckBox.isEnabled = !autoValueEnabled
        }
    }
}
