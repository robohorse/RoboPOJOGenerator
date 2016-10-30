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
    public void itemStateChanged(ItemEvent e) {
        final boolean kotlinEnabled = e.getStateChange() == ItemEvent.SELECTED;
        enableAutoValueButton(generatorVew, !kotlinEnabled);
    }

    private void enableAutoValueButton(GeneratorVew generatorVew, boolean enable) {
        final ButtonGroup buttonGroup = generatorVew.getTypeButtonGroup();
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons
                .hasMoreElements(); ) {
            final AbstractButton button = buttons.nextElement();
            if (AnnotationEnum.AUTO_VALUE_GSON.getText().equals(button.getText())) {
                button.setEnabled(enable);

            } else if (AnnotationEnum.NONE.getText().equals(button.getText())) {
                button.setSelected(true);
            }
        }
    }
}
