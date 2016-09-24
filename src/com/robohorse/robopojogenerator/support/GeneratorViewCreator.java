package com.robohorse.robopojogenerator.support;

import com.robohorse.robopojogenerator.action.GuiFormEventListener;
import com.robohorse.robopojogenerator.errors.JSONStructureException;
import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.view.GeneratorVew;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by vadim on 24.09.16.
 */
public class GeneratorViewCreator {
    private GuiFormEventListener eventListener;
    private MessageService messageService;

    public GeneratorViewCreator(MessageService messageService) {
        this.messageService = messageService;
    }

    public void setGuiFormEventListener(GuiFormEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void showView() {
        GeneratorVew generatorVew = new GeneratorVew();
        generatorVew.getGenerateButton()
                .addActionListener(new GenerateActionListener(generatorVew));

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

    private void validateJsonContent(String content) throws RoboPluginException {
        try {
            JSONObject jsonObject = new JSONObject(content);
        } catch (JSONException exception) {
            throw new JSONStructureException();
        }
    }

    private class GenerateActionListener implements ActionListener {
        private GeneratorVew generatorVew;

        public GenerateActionListener(GeneratorVew generatorVew) {
            this.generatorVew = generatorVew;
        }

        public void actionPerformed(ActionEvent e) {
            JTextArea textArea = generatorVew.getTextArea();
            try {
                final String text = textArea.getText();
                validateJsonContent(text);
                eventListener.onJsonDataObtained(text);

            } catch (RoboPluginException exception) {
                messageService.onPluginExceptionHandled(exception);
            }
        }
    }
}
