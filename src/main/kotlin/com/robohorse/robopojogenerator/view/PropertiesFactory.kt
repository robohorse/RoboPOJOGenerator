package com.robohorse.robopojogenerator.view

import com.robohorse.robopojogenerator.view.AdditionalPropertiesVM.UseGetters
import com.robohorse.robopojogenerator.view.AdditionalPropertiesVM.UseJavaPrimitives
import com.robohorse.robopojogenerator.view.AdditionalPropertiesVM.UseKotlinNullableFields
import com.robohorse.robopojogenerator.view.AdditionalPropertiesVM.UseKotlinParcelable
import com.robohorse.robopojogenerator.view.AdditionalPropertiesVM.UseKotlinSingleDataClass
import com.robohorse.robopojogenerator.view.AdditionalPropertiesVM.UseLombokValue
import com.robohorse.robopojogenerator.view.AdditionalPropertiesVM.UseSetters
import com.robohorse.robopojogenerator.view.AdditionalPropertiesVM.UseToString
import com.robohorse.robopojogenerator.view.FrameworkVW.AutoValue
import com.robohorse.robopojogenerator.view.FrameworkVW.FastJson
import com.robohorse.robopojogenerator.view.FrameworkVW.Gson
import com.robohorse.robopojogenerator.view.FrameworkVW.Jackson
import com.robohorse.robopojogenerator.view.FrameworkVW.LoganSquare
import com.robohorse.robopojogenerator.view.FrameworkVW.Moshi
import com.robohorse.robopojogenerator.view.FrameworkVW.None
import com.robohorse.robopojogenerator.view.FrameworkVW.NoneLombok
import com.robohorse.robopojogenerator.view.LanguageVM.Java
import com.robohorse.robopojogenerator.view.LanguageVM.Kotlin
import com.robohorse.robopojogenerator.view.SourceVM.Json
import com.robohorse.robopojogenerator.view.SourceVM.JsonSchema

internal class PropertiesFactory {

    fun createControls(): ControlsModel {
        val result = ControlsModel(
            sources = listOf(
                Json(languages = createJsonLanguages()),
                JsonSchema(languages = createJsonLanguages())
            )
        )
        result.selectedSource = result.sources[0]
        result.selectedSource?.let { source ->
            source.selectedLanguage = source.languages[0]
            source.selectedLanguage?.let { language ->
                language.selectedFramework = language.frameworks[0]
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
