package com.robohorse.robopojogenerator.view

import com.robohorse.robopojogenerator.view.AdditionalPropertiesVM.*
import com.robohorse.robopojogenerator.view.FrameworkVW.*
import com.robohorse.robopojogenerator.view.LanguageVM.Java
import com.robohorse.robopojogenerator.view.LanguageVM.Kotlin
import com.robohorse.robopojogenerator.view.SourceVM.Json
import com.robohorse.robopojogenerator.view.SourceVM.JsonSchema

class PropertiesFactory {

    fun createControls(): ControlsModel {
        val result = ControlsModel(sources = listOf(
                Json(languages = createJsonLanguages()),
                JsonSchema(languages = createJsonLanguages())
        ))
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
            Java(frameworks = listOf(
                    None(properties = listOf(
                            UseSetters(),
                            UseGetters(selected = true),
                            UseToString()
                    )),
                    Gson(properties = listOf(
                            UseSetters(),
                            UseGetters(selected = true),
                            UseToString()
                    )),
                    Jackson(properties = listOf(
                            UseSetters(),
                            UseGetters(selected = true),
                            UseToString()
                    )),
                    LoganSquare(properties = listOf(
                            UseSetters(),
                            UseGetters(selected = true),
                            UseToString()
                    )),
                    Moshi(properties = listOf(
                            UseSetters(),
                            UseGetters(selected = true),
                            UseToString()
                    )),
                    FastJson(properties = listOf(
                            UseSetters(),
                            UseGetters(selected = true),
                            UseToString()
                    )),
                    AutoValue()
            )
            ),
            Kotlin(frameworks = listOf(
                    None(),
                    Gson(),
                    Jackson(),
                    LoganSquare(),
                    Moshi(),
                    FastJson()
            )
            )
    )
}
