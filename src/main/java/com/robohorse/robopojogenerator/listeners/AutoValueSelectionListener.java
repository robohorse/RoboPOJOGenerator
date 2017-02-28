package com.robohorse.robopojogenerator.listeners;

import com.robohorse.robopojogenerator.view.ui.GeneratorVew;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by vadim on 28.02.17.
 */
public class AutoValueSelectionListener implements ItemListener {
    private GeneratorVew generatorVew;

    public AutoValueSelectionListener(GeneratorVew generatorVew) {
        this.generatorVew = generatorVew;
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        final boolean autoValueEnabled = itemEvent.getStateChange() == ItemEvent.SELECTED;
        enableCheckBoxes(generatorVew, autoValueEnabled);
    }

    private void enableCheckBoxes(GeneratorVew generatorVew, boolean autoValueEnabled) {
        generatorVew.getUseGettersCheckBox().setEnabled(!autoValueEnabled);
        generatorVew.getUseSettersCheckBox().setEnabled(!autoValueEnabled);
        generatorVew.getUseStringCheckBox().setEnabled(!autoValueEnabled);
        generatorVew.getKotlinCheckBox().setEnabled(!autoValueEnabled);
    }
}
