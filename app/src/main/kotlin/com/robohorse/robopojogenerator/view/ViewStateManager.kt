package com.robohorse.robopojogenerator.view

import com.robohorse.robopojogenerator.form.GeneratorVew
import com.robohorse.robopojogenerator.models.*
import com.robohorse.robopojogenerator.persistense.ViewStateService

internal class ViewStateManager(
    private val viewStateService: ViewStateService
) {

    fun restoreCommonProperties(generatorVew: GeneratorVew) {
        viewStateService.state.model?.let { model ->
            with(generatorVew) {
                useTabsIndentation.isSelected = model.useTabsIndentation
                rewriteExistingClassesCheckBox.isSelected = model.rewriteClasses
                className.text = model.rootClassName
            }
        }
    }

    fun restoreState(properties: ControlsModel?) {
        viewStateService.state.model?.let { model ->
            properties?.apply {
                sources.filterIsInstance<SourceVM.Json>().firstOrNull()?.apply {
                    applyLanguage(this, model)
                }
            }
        }
    }

    private fun applyLanguage(source: SourceVM, model: GenerationModel) {
        source.selectedLanguage = if (model.useKotlin) {
            source.languages.filterIsInstance<LanguageVM.Kotlin>().firstOrNull()
        } else {
            source.languages.filterIsInstance<LanguageVM.Java>().firstOrNull()
        }
        source.selectedLanguage?.let {
            applyFrameworks(it, model)
        }
    }

    private fun applyFrameworks(language: LanguageVM, model: GenerationModel) {
        language.selectedFramework =
            language.frameworks.firstOrNull { it.propertyName == model.annotationEnum.propertyName }
        language.selectedFramework?.let {
            applyProperties(it, model)
        }
    }

    private fun applyProperties(framework: FrameworkVW, model: GenerationModel) {
        with(framework.properties) {
            filterIsInstance<AdditionalPropertiesVM.UseSetters>().firstOrNull()?.selected =
                model.useSetters
            filterIsInstance<AdditionalPropertiesVM.UseGetters>().firstOrNull()?.selected =
                model.useGetters
            filterIsInstance<AdditionalPropertiesVM.UseToString>().firstOrNull()?.selected =
                model.useStrings
            filterIsInstance<AdditionalPropertiesVM.UseJavaPrimitives>().firstOrNull()?.selected =
                model.javaPrimitives
            filterIsInstance<AdditionalPropertiesVM.UseKotlinParcelable>().firstOrNull()?.selected =
                model.useKotlinParcelable
            filterIsInstance<AdditionalPropertiesVM.UseKotlinSingleDataClass>().firstOrNull()?.selected =
                model.useKotlinSingleDataClass
            filterIsInstance<AdditionalPropertiesVM.UseKotlinNullableFields>().firstOrNull()?.selected =
                model.kotlinNullableFields
            filterIsInstance<AdditionalPropertiesVM.UseLombokValue>().firstOrNull()?.selected =
                model.useLombokValue
            filterIsInstance<AdditionalPropertiesVM.UseMoshiAdapterAnnotation>().firstOrNull()?.selected =
                model.useMoshiAdapter
        }
    }
}
