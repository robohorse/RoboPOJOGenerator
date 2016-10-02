package com.robohorse.robopojogenerator.view;

import com.intellij.openapi.ui.DialogBuilder;
import com.robohorse.robopojogenerator.action.GenerateActionListener;
import com.robohorse.robopojogenerator.action.GuiFormEventListener;
import com.robohorse.robopojogenerator.generator.consts.AnnotationItem;
import com.robohorse.robopojogenerator.view.GeneratorVew;

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
        GeneratorVew generatorVew = new GeneratorVew();
        generatorVew.getGenerateButton()
                .addActionListener(new GenerateActionListener(generatorVew, eventListener));
        bindGroupViews(generatorVew.getTypeButtonGroup());

        builder.setCenterPanel(generatorVew.getRootView());
        builder.setTitle("RoboPOJOGenerator");
        builder.removeAllActions();
        builder.show();
    }

    private void bindGroupViews(ButtonGroup buttonGroup) {
        final Enumeration<AbstractButton> buttons = buttonGroup.getElements();
        for (AnnotationItem annotationItems : AnnotationItem.values()) {
            if (buttons.hasMoreElements()) {
                final AbstractButton button = buttons.nextElement();
                button.setText(annotationItems.getText());
            } else {
                break;
            }
        }
    }
}
