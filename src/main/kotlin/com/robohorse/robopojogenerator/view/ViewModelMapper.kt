package com.robohorse.robopojogenerator.view

import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.view.AdditionalPropertiesVM.Companion.GETTERS
import com.robohorse.robopojogenerator.view.AdditionalPropertiesVM.Companion.SETTERS
import com.robohorse.robopojogenerator.view.AdditionalPropertiesVM.Companion.TO_STRING

class ViewModelMapper(
        private val generatorViewBinder: GeneratorViewBinder
) {

    fun map(generatorVew: GeneratorVew) =
            with(generatorVew) {
                GenerationModel(
                        rewriteClasses = rewriteExistingClassesCheckBox.isSelected,
                        annotationEnum = resolveFramework(),
                        useKotlin = isKotlinSelected(),
                        useSetters = resolveCheckBox(SETTERS),
                        useGetters = resolveCheckBox(GETTERS),
                        useStrings = resolveCheckBox(TO_STRING),
                        content = textArea.text,
                        rootClassName = classNameTextField.text
                )
            }

    private fun resolveCheckBox(key: String) = with(generatorViewBinder.properties) {
        with(this?.selectedSource?.selectedLanguage?.selectedFramework) {
            this?.properties?.firstOrNull { it.propertyName == key }?.selected
        }
    } ?: false

    private fun resolveFramework() = with(generatorViewBinder.properties) {
        this?.selectedSource?.selectedLanguage?.selectedFramework ?: throw IllegalStateException()
    }

    private fun isKotlinSelected() = with(generatorViewBinder.properties) {
        this?.selectedSource?.selectedLanguage is LanguageVM.Kotlin
    }
}
