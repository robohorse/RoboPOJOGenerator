package com.robohorse.robopojogenerator.utils;

import com.robohorse.robopojogenerator.action.GenerateActionListener;
import com.robohorse.robopojogenerator.action.GuiFormEventListener;
import com.robohorse.robopojogenerator.generator.AnnotationItem;
import com.robohorse.robopojogenerator.view.GeneratorVew;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
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

    public void showView() {
        JFrame frame = new JFrame("RoboPOJOGenerator");
        GeneratorVew generatorVew = new GeneratorVew();
        generatorVew.getGenerateButton()
                .addActionListener(new GenerateActionListener(generatorVew, frame, eventListener));

        frame.setContentPane(generatorVew.getRootView());

        frame.pack();
        bindGroupViews(generatorVew.getTypeButtonGroup());

        centerView(frame);
        frame.setVisible(true);
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

    private void centerView(Frame frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
}
