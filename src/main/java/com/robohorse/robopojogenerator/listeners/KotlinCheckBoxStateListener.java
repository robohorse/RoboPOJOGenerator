package com.robohorse.robopojogenerator.listeners;

import com.robohorse.robopojogenerator.generator.consts.annotations.AnnotationEnum;
import com.robohorse.robopojogenerator.view.ui.GeneratorVew;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;

/**
 * Created by vadim on 29.10.16.
 */
public class KotlinCheckBoxStateListener implements ItemListener {
    private GeneratorVew generatorVew;

    public KotlinCheckBoxStateListener(GeneratorVew generatorVew) {
        this.generatorVew = generatorVew;
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        final boolean kotlinEnabled = itemEvent.getStateChange() == ItemEvent.SELECTED;
        enableRadioButton(generatorVew, !kotlinEnabled);
        enableCheckBoxes(generatorVew, kotlinEnabled);
    }

    private void enableCheckBoxes(GeneratorVew generatorVew, boolean kotlinEnabled) {
        generatorVew.getUseGettersCheckBox().setEnabled(!kotlinEnabled);
        generatorVew.getUseSettersCheckBox().setEnabled(!kotlinEnabled);
        generatorVew.getUseStringCheckBox().setEnabled(!kotlinEnabled);
    }

    private void enableRadioButton(GeneratorVew generatorVew, boolean enable) {
        final ButtonGroup buttonGroup = generatorVew.getTypeButtonGroup();
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons
                .hasMoreElements(); ) {
            final AbstractButton button = buttons.nextElement();
            if (AnnotationEnum.AUTO_VALUE_GSON.getText().equals(button.getText())) {
                button.setEnabled(enable);

            } else if (AnnotationEnum.NONE.getText().equals(button.getText())) {
                button.setSelected(true);

            } else if (AnnotationEnum.FAST_JSON.getText().equals(button.getText())){
                button.setEnabled(enable);
            }
        }
    }
}
