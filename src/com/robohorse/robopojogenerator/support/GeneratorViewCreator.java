package com.robohorse.robopojogenerator.support;

import com.robohorse.robopojogenerator.action.GenerateActionListener;
import com.robohorse.robopojogenerator.action.GuiFormEventListener;
import com.robohorse.robopojogenerator.view.GeneratorVew;

import javax.swing.*;
import java.awt.*;

/**
 * Created by vadim on 24.09.16.
 */
public class GeneratorViewCreator {
    private GuiFormEventListener eventListener;

    public void setGuiFormEventListener(GuiFormEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void showView() {
        GeneratorVew generatorVew = new GeneratorVew();
        generatorVew.getGenerateButton()
                .addActionListener(new GenerateActionListener(generatorVew, eventListener));

        JFrame frame = new JFrame("RoboPOJOGenerator");
        frame.setContentPane(generatorVew.getRootView());

        frame.pack();
        centerView(frame);
        frame.setVisible(true);
    }

    private void centerView(Frame frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
}
