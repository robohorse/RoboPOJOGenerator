package com.robohorse.robopojogenerator.support;

import com.robohorse.robopojogenerator.view.GeneratorVew;

import javax.swing.*;
import java.awt.*;

/**
 * Created by vadim on 24.09.16.
 */
public class GeneratorViewCreator {

    public void showView() {
        JFrame frame = new JFrame("RoboPOJOGenerator");
        frame.setContentPane(new GeneratorVew().getRootView());
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
