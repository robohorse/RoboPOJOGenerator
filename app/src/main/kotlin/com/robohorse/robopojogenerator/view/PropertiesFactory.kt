package com.robohorse.robopojogenerator.view

import com.robohorse.robopojogenerator.models.AdditionalPropertiesVM.UseGetters
import com.robohorse.robopojogenerator.models.AdditionalPropertiesVM.UseJavaPrimitives
import com.robohorse.robopojogenerator.models.AdditionalPropertiesVM.UseKotlinNullableFields
import com.robohorse.robopojogenerator.models.AdditionalPropertiesVM.UseKotlinParcelable
import com.robohorse.robopojogenerator.models.AdditionalPropertiesVM.UseKotlinSingleDataClass
import com.robohorse.robopojogenerator.models.AdditionalPropertiesVM.UseLombokValue
import com.robohorse.robopojogenerator.models.AdditionalPropertiesVM.UseSetters
import com.robohorse.robopojogenerator.models.AdditionalPropertiesVM.UseToString
import com.robohorse.robopojogenerator.models.ControlsModel
import com.robohorse.robopojogenerator.models.FrameworkVW.AutoValue
import com.robohorse.robopojogenerator.models.FrameworkVW.FastJson
import com.robohorse.robopojogenerator.models.FrameworkVW.Gson
import com.robohorse.robopojogenerator.models.FrameworkVW.Jackson
import com.robohorse.robopojogenerator.models.FrameworkVW.LoganSquare
import com.robohorse.robopojogenerator.models.FrameworkVW.Moshi
import com.robohorse.robopojogenerator.models.FrameworkVW.None
import com.robohorse.robopojogenerator.models.FrameworkVW.NoneLombok
import com.robohorse.robopojogenerator.models.LanguageVM.Java
import com.robohorse.robopojogenerator.models.LanguageVM.Kotlin
import com.robohorse.robopojogenerator.models.SourceVM.Json
import com.robohorse.robopojogenerator.models.SourceVM.JsonSchema

internal class PropertiesFactory {

    fun createControls(): ControlsModel {
        val result = ControlsModel(
            sources = listOf(
                Json(languages = createJsonLanguages()),
                JsonSchema(languages = createJsonLanguages())
            )
        )
        result.selectedSource = result.sources.first()
        result.selectedSource?.let { source ->
            source.selectedLanguage = source.languages.first()
            source.selectedLanguage?.let { language ->
                language.selectedFramework = language.frameworks.first()
            }
        }
        return result
    }

    private fun createJsonLanguages() = listOf(
        Java(
            frameworks = listOf(
                None(
                    properties = listOf(
                        UseJavaPrimitives(selected = true),
                        UseSetters(),
                        UseGetters(selected = true),
                        UseToString()
                    )
                ),
                NoneLombok(
                    properties = listOf(
                        UseJavaPrimitives(selected = true),
                        UseLombokValue()
                    )
                ),
                Gson(
                    properties = listOf(
                        UseJavaPrimitives(selected = true),
                        UseSetters(),
                        UseGetters(selected = true),
                        UseToString()
                    )
                ),
                Jackson(
                    properties = listOf(
                        UseJavaPrimitives(selected = true),
                        UseSetters(),
                        UseGetters(selected = true),
                        UseToString()
                    )
                ),
                LoganSquare(
                    properties = listOf(
                        UseJavaPrimitives(selected = true),
                        UseSetters(),
                        UseGetters(selected = true),
                        UseToString()
                    )
                ),
                Moshi(
                    properties = listOf(
                        UseJavaPrimitives(selected = true),
                        UseSetters(),
                        UseGetters(selected = true),
                        UseToString()
                    )
                ),
                FastJson(
                    properties = listOf(
                        UseJavaPrimitives(selected = true),
                        UseSetters(),
                        UseGetters(selected = true),
                        UseToString()
                    )
                ),
                AutoValue()
            )
        ),
        Kotlin(
            frameworks = listOf(
                None(
                    properties = listOf(
                        UseKotlinSingleDataClass(selected = true),
                        UseKotlinNullableFields(selected = true),
                        UseKotlinParcelable()
                    )
                ),
                Gson(
                    properties = listOf(
                        UseKotlinSingleDataClass(selected = true),
                        UseKotlinNullableFields(selected = true),
                        UseKotlinParcelable()
                    )
                ),
                Jackson(
                    properties = listOf(
                        UseKotlinSingleDataClass(selected = true),
                        UseKotlinNullableFields(selected = true),
                        UseKotlinParcelable()
                    )
                ),
                LoganSquare(
                    properties = listOf(
                        UseKotlinSingleDataClass(selected = true),
                        UseKotlinNullableFields(selected = true),
                        UseKotlinParcelable()
                    )
                ),
                Moshi(
                    properties = listOf(
                        UseKotlinSingleDataClass(selected = true),
                        UseKotlinNullableFields(selected = true),
                        UseKotlinParcelable()
                    )
                ),
                FastJson(
                    properties = listOf(
                        UseKotlinSingleDataClass(selected = true),
                        UseKotlinNullableFields(selected = true),
                        UseKotlinParcelable()
                    )
                )
            )
        )
    )
}