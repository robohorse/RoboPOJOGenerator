package com.robohorse.robopojogenerator;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import javax.swing.*;
import java.awt.*;

/**
 * Created by vadim on 24.09.16.
 */
public class NewPOJOGroupAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        JFrame frame = new JFrame("RoboPOJOGenerator");
        frame.setContentPane(new GeneratorVew().getRootView());

        frame.pack();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);


        frame.setVisible(true);
    }
}
