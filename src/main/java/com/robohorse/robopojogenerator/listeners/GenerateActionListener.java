package com.robohorse.robopojogenerator.listeners;

import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.generator.consts.annotations.AnnotationEnum;
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper;
import com.robohorse.robopojogenerator.injections.Injector;
import com.robohorse.robopojogenerator.models.GenerationModel;
import com.robohorse.robopojogenerator.delegates.MessageDelegate;
import com.robohorse.robopojogenerator.view.ui.GeneratorVew;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

/**
 * Created by vadim on 24.09.16.
 */
public class GenerateActionListener implements ActionListener {
    private GuiFormEventListener eventListener;
    private GeneratorVew generatorVew;

    @Inject
    MessageDelegate messageDelegate;
    @Inject
    ClassGenerateHelper classGenerateHelper;

    public GenerateActionListener(GeneratorVew generatorVew,
                                  GuiFormEventListener eventListener) {
        this.generatorVew = generatorVew;
        this.eventListener = eventListener;
        Injector.getAppComponent().inject(this);
    }

    public void actionPerformed(ActionEvent e) {
        final JTextArea textArea = generatorVew.getTextArea();
        final JTextField textField = generatorVew.getClassNameTextField();

        final AnnotationEnum annotationEnum = resolveAnnotationItem();

        final boolean useKotlin = generatorVew.getKotlinCheckBox().isSelected();
        final boolean rewriteClasses = generatorVew.getRewriteExistingClassesCheckBox().isSelected();
        final boolean useSetters = generatorVew.getUseSettersCheckBox().isSelected();
        final boolean useGetters = generatorVew.getUseGettersCheckBox().isSelected();
        final boolean useStrings = generatorVew.getUseStringCheckBox().isSelected();

        final String content = textArea.getText();
        final String className = textField.getText();
        try {
            classGenerateHelper.validateClassName(className);
            classGenerateHelper.validateJsonContent(content);
            eventListener.onJsonDataObtained(new GenerationModel
                    .Builder()
                    .useKotlin(useKotlin)
                    .setAnnotationItem(annotationEnum)
                    .setContent(content)
                    .setSettersAvailable(useSetters)
                    .setGettersAvailable(useGetters)
                    .setToStringAvailable(useStrings)
                    .setRootClassName(className)
                    .setRewriteClasses(rewriteClasses)
                    .build());

        } catch (RoboPluginException exception) {
            messageDelegate.onPluginExceptionHandled(exception);
        }
    }

    private AnnotationEnum resolveAnnotationItem() {
        final ButtonGroup buttonGroup = generatorVew.getTypeButtonGroup();
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons
                .hasMoreElements(); ) {
            final AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                for (AnnotationEnum annotationEnum : AnnotationEnum.values()) {
                    if (annotationEnum.getText().equals(button.getText())) {
                        return annotationEnum;
                    }
                }
            }
        }
        return AnnotationEnum.NONE;
    }
}
