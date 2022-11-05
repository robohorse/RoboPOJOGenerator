package com.robohorse.robopojogenerator.view

import com.robohorse.robopojogenerator.form.GeneratorVew
import com.robohorse.robopojogenerator.models.AdditionalPropertiesVM.Companion.GETTERS
import com.robohorse.robopojogenerator.models.AdditionalPropertiesVM.Companion.JAVA_PRIMITIVE_TYPES
import com.robohorse.robopojogenerator.models.AdditionalPropertiesVM.Companion.KOTLIN_NULLABLE_FIELDS
import com.robohorse.robopojogenerator.models.AdditionalPropertiesVM.Companion.KOTLIN_PARCELABLE
import com.robohorse.robopojogenerator.models.AdditionalPropertiesVM.Companion.KOTLIN_SINGLE_DATA_CLASS
import com.robohorse.robopojogenerator.models.AdditionalPropertiesVM.Companion.LOMBOK_VALUE
import com.robohorse.robopojogenerator.models.AdditionalPropertiesVM.Companion.MOSHI_ADAPTER
import com.robohorse.robopojogenerator.models.AdditionalPropertiesVM.Companion.SETTERS
import com.robohorse.robopojogenerator.models.AdditionalPropertiesVM.Companion.TO_STRING
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.models.LanguageVM

internal class ViewModelMapper(
    private val generatorViewBinder: GeneratorViewBinder
) {

    fun map(generatorVew: GeneratorVew) = with(generatorVew) {
        GenerationModel(
            rewriteClasses = rewriteExistingClassesCheckBox.isSelected,
            annotationEnum = resolveFramework(),
            useKotlin = isKotlinSelected(),
            content = textArea.text,
            rootClassName = className.text,
            useSetters = resolveCheckBox(SETTERS),
            useGetters = resolveCheckBox(GETTERS),
            useStrings = resolveCheckBox(TO_STRING),
            useKotlinParcelable = resolveCheckBox(KOTLIN_PARCELABLE),
            useKotlinSingleDataClass = resolveCheckBox(KOTLIN_SINGLE_DATA_CLASS),
            kotlinNullableFields = resolveCheckBox(KOTLIN_NULLABLE_FIELDS),
            javaPrimitives = resolveCheckBox(JAVA_PRIMITIVE_TYPES),
            useLombokValue = resolveCheckBox(LOMBOK_VALUE),
            useTabsIndentation = useTabsIndentation.isSelected,
            useMoshiAdapter = resolveCheckBox(MOSHI_ADAPTER)
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
