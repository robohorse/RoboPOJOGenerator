package com.robohorse.robopojogenerator.listeners;

import com.robohorse.robopojogenerator.view.ui.GeneratorVew;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class FastJsonSelectionListener implements ItemListener {
    private GeneratorVew generatorVew;

    public FastJsonSelectionListener(GeneratorVew generatorVew) {
        this.generatorVew = generatorVew;
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        final boolean autoValueEnabled = itemEvent.getStateChange() == ItemEvent.SELECTED;
        enableCheckBoxes(generatorVew, autoValueEnabled);
    }

    private void enableCheckBoxes(GeneratorVew generatorVew, boolean autoValueEnabled) {
        generatorVew.getKotlinCheckBox().setEnabled(!autoValueEnabled);
    }
}
