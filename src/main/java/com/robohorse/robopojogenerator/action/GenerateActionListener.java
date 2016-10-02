package com.robohorse.robopojogenerator.action;

import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.errors.custom.JSONStructureException;
import com.robohorse.robopojogenerator.errors.custom.WrongClassNameException;
import com.robohorse.robopojogenerator.generator.consts.AnnotationItem;
import com.robohorse.robopojogenerator.injections.Injector;
import com.robohorse.robopojogenerator.model.GenerationModel;
import com.robohorse.robopojogenerator.utils.MessageService;
import com.robohorse.robopojogenerator.view.GeneratorVew;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

/**
 * Created by vadim on 24.09.16.
 */
public class GenerateActionListener implements ActionListener {
    @Inject
    MessageService messageService;
    private GuiFormEventListener eventListener;
    private GeneratorVew generatorVew;

    public GenerateActionListener(GeneratorVew generatorVew,
                                  GuiFormEventListener eventListener) {
        this.generatorVew = generatorVew;
        this.eventListener = eventListener;
        Injector.getAppComponent().inject(this);
    }

    public void actionPerformed(ActionEvent e) {
        final JTextArea textArea = generatorVew.getTextArea();
        final JTextField jTextField = generatorVew.getClassNameTextField();

        final AnnotationItem annotationItem = resolveAnnotationItem();
        final boolean rewriteClasses = generatorVew.getRewriteExistingClassesCheckBox().isSelected();
        final String content = textArea.getText();
        final String className = jTextField.getText();
        try {
            validateClassName(className);
            validateJsonContent(content);
            eventListener.onJsonDataObtained(new GenerationModel
                    .Builder()
                    .setAnnotationItem(annotationItem)
                    .setContent(content)
                    .setRootClassName(className)
                    .setRewriteClasses(rewriteClasses)
                    .build());

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

    private AnnotationItem resolveAnnotationItem() {
        ButtonGroup buttonGroup = generatorVew.getTypeButtonGroup();
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                for (AnnotationItem annotationItem : AnnotationItem.values()) {
                    if (annotationItem.getText().equals(button.getText())) {
                        return annotationItem;
                    }
                }
            }
        }
        return AnnotationItem.NONE;
    }
}
