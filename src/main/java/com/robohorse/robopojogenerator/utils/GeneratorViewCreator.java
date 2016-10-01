package com.robohorse.robopojogenerator.utils;

import com.intellij.openapi.ui.DialogBuilder;
import com.robohorse.robopojogenerator.action.GenerateActionListener;
import com.robohorse.robopojogenerator.action.GuiFormEventListener;
import com.robohorse.robopojogenerator.generator.AnnotationItem;
import com.robohorse.robopojogenerator.view.GeneratorVew;

import javax.inject.Inject;
import javax.swing.*;
import java.util.Enumeration;

/**
 * Created by vadim on 24.09.16.
 */
public class GeneratorViewCreator {
    @Inject
    public GeneratorViewCreator() {
    }

    private GuiFormEventListener eventListener;

    public void setGuiFormEventListener(GuiFormEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public DialogBuilder createView() {
        return new DialogBuilder();
    }

    public void bindView(DialogBuilder builder) {
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
        Enumeration<AbstractButton> buttons = buttonGroup.getElements();
        for (AnnotationItem annotationItems : AnnotationItem.values()) {
            if (buttons.hasMoreElements()) {
                AbstractButton button = buttons.nextElement();
                button.setText(annotationItems.getText());

            } else {
                break;
            }
        }
    }
}
