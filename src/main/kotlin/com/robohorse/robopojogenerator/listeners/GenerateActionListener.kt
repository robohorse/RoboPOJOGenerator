package com.robohorse.robopojogenerator.listeners

import com.robohorse.robopojogenerator.delegates.MessageDelegate
import com.robohorse.robopojogenerator.errors.RoboPluginException
import com.robohorse.robopojogenerator.generator.consts.annotations.AnnotationEnum
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.view.GeneratorVew
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class GenerateActionListener(
        private val generatorVew: GeneratorVew,
        private val eventListener: GuiFormEventListener,
        private val messageDelegate: MessageDelegate,
        private val classGenerateHelper: ClassGenerateHelper
) : ActionListener {

    override fun actionPerformed(e: ActionEvent) {
        with(generatorVew) {
            val textField = classNameTextField
            var content = textArea.text
            val className = textField.text
            try {
                classGenerateHelper.validateClassName(className)
                content = classGenerateHelper.validateJsonContent(content)
                eventListener.onJsonDataObtained(GenerationModel(
                        rewriteClasses = rewriteExistingClassesCheckBox.isSelected,
                        annotationEnum = resolveAnnotationItem(),
                        useKotlin = kotlinCheckBox.isSelected,
                        useSetters = useSettersCheckBox.isSelected,
                        useGetters = useGettersCheckBox.isSelected,
                        useStrings = useStringCheckBox.isSelected,
                        content = content,
                        rootClassName = className
                ))
            } catch (exception: RoboPluginException) {
                messageDelegate.onPluginExceptionHandled(exception)
            }
        }
    }

    private fun resolveAnnotationItem(): AnnotationEnum {
        val buttonGroup = generatorVew.typeButtonGroup
        val buttons = buttonGroup.elements
        while (buttons.hasMoreElements()) {
            val button = buttons.nextElement()
            if (button.isSelected) {
                for (annotationEnum in AnnotationEnum.values()) {
                    if (annotationEnum.text == button.text) {
                        return annotationEnum
                    }
                }
            }
        }
        return AnnotationEnum.NONE
    }
}
