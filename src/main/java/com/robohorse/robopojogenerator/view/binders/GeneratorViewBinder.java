package com.robohorse.robopojogenerator.view.binders;

import com.intellij.openapi.ui.DialogBuilder;
import com.robohorse.robopojogenerator.generator.consts.annotations.AnnotationEnum;
import com.robohorse.robopojogenerator.listeners.AutoValueSelectionListener;
import com.robohorse.robopojogenerator.listeners.GenerateActionListener;
import com.robohorse.robopojogenerator.listeners.GuiFormEventListener;
import com.robohorse.robopojogenerator.listeners.KotlinCheckBoxStateListener;
import com.robohorse.robopojogenerator.view.ui.GeneratorVew;

import javax.inject.Inject;
import javax.swing.*;
import java.util.Enumeration;

/**
 * Created by vadim on 24.09.16.
 */
public class GeneratorViewBinder {
    @Inject
    public GeneratorViewBinder() {
    }

    public void bindView(DialogBuilder builder, GuiFormEventListener eventListener) {
        final GeneratorVew generatorVew = new GeneratorVew();
        final GenerateActionListener actionListener = new GenerateActionListener(generatorVew, eventListener);
        generatorVew.getGenerateButton().addActionListener(actionListener);
        generatorVew.getKotlinCheckBox().addItemListener(new KotlinCheckBoxStateListener(generatorVew));

        bindGroupViews(generatorVew.getTypeButtonGroup(), generatorVew);

        builder.setCenterPanel(generatorVew.getRootView());
        builder.setTitle("RoboPOJOGenerator");
        builder.removeAllActions();
        builder.show();
    }

    private void bindGroupViews(ButtonGroup buttonGroup, GeneratorVew generatorVew) {
        final Enumeration<AbstractButton> buttons = buttonGroup.getElements();
        for (AnnotationEnum annotationItems : AnnotationEnum.values()) {
            if (buttons.hasMoreElements()) {
                final AbstractButton button = buttons.nextElement();
                button.setText(annotationItems.getText());
                if (annotationItems == AnnotationEnum.AUTO_VALUE_GSON) {
                    button.addItemListener(new AutoValueSelectionListener(generatorVew));
                }
            } else {
                break;
            }
        }
    }
}
