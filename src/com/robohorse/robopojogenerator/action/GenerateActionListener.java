package com.robohorse.robopojogenerator.action;

import com.robohorse.robopojogenerator.errors.JSONStructureException;
import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.errors.WrongClassNameException;
import com.robohorse.robopojogenerator.generator.AnnotationItem;
import com.robohorse.robopojogenerator.utils.MessageService;
import com.robohorse.robopojogenerator.view.GeneratorVew;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by vadim on 24.09.16.
 */
public class GenerateActionListener implements ActionListener {
    private GuiFormEventListener eventListener;
    private MessageService messageService = new MessageService();
    private GeneratorVew generatorVew;

    public GenerateActionListener(GeneratorVew generatorVew, GuiFormEventListener eventListener) {
        this.generatorVew = generatorVew;
        this.eventListener = eventListener;
    }

    public void actionPerformed(ActionEvent e) {
        final JTextArea textArea = generatorVew.getTextArea();
        final JTextField jTextField = generatorVew.getClassNameTextField();
        final JRadioButton jRadioButtonGSON = generatorVew.getRadioGson();

        AnnotationItem annotationItem;
        if (jRadioButtonGSON.isSelected()) {
            annotationItem = AnnotationItem.GSON;

        } else {
            annotationItem = AnnotationItem.LOGAN_SQUARE;
        }

        try {
            final String text = textArea.getText();
            final String className = jTextField.getText();
            validateClassName(className);

            validateJsonContent(text);
            eventListener.onJsonDataObtained(text, className, annotationItem);

        } catch (RoboPluginException exception) {
            messageService.onPluginExceptionHandled(exception);
        }
    }

    private void validateJsonContent(String content) throws RoboPluginException {
        try {
            JSONObject jsonObject = new JSONObject(content);
        } catch (JSONException exception) {
            throw new JSONStructureException();
        }
    }

    private void validateClassName(String name) throws RoboPluginException {
        if (null != name && name.length() > 1) {
            String pattern = "^[a-zA-Z0-9]*$";
            if (name.matches(pattern)) {
                return;
            }
        }
        throw new WrongClassNameException();
    }
}
