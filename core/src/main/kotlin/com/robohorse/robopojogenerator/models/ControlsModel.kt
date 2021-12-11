package com.robohorse.robopojogenerator.models

data class ControlsModel(
    val sources: List<SourceVM>,
    var selectedSource: SourceVM? = null
)

sealed class SourceVM(
    val languages: List<LanguageVM>,
    var selectedLanguage: LanguageVM? = null,
    val propertyName: String
) {
    class Json(
        languages: List<LanguageVM>,
        selected: LanguageVM? = null
    ) : SourceVM(languages, selected, JSON)

    class JsonSchema(
        languages: List<LanguageVM>,
        selected: LanguageVM? = null
    ) : SourceVM(languages, selected, JSON_SCHEMA)

    companion object {
        const val JSON = "Json"
        const val JSON_SCHEMA = "Json Schema"
    }
}

sealed class LanguageVM(
    val frameworks: List<FrameworkVW>,
    var selectedFramework: FrameworkVW? = null,
    val propertyName: String
) {
    class Java(
        frameworks: List<FrameworkVW>,
        selected: FrameworkVW? = null
    ) : LanguageVM(frameworks, selected, JAVA)

    class Kotlin(
        frameworks: List<FrameworkVW>,
        selected: FrameworkVW? = null
    ) : LanguageVM(frameworks, selected, KOTLIN)

    companion object {
        const val JAVA = "Java"
        const val KOTLIN = "Kotlin"
    }
}

sealed class FrameworkVW(
    val propertyName: String,
    val properties: List<AdditionalPropertiesVM>
) {
    class None(
        properties: List<AdditionalPropertiesVM> = emptyList()
    ) : FrameworkVW(NONE, properties)

    class NoneLombok(
        properties: List<AdditionalPropertiesVM> = emptyList()
    ) : FrameworkVW(NONE_LOMBOK, properties)

    class Gson(
        properties: List<AdditionalPropertiesVM> = emptyList()
    ) : FrameworkVW(GSON, properties)

    class Jackson(
        properties: List<AdditionalPropertiesVM> = emptyList()
    ) : FrameworkVW(JACKSON, properties)

    class LoganSquare(
        properties: List<AdditionalPropertiesVM> = emptyList()
    ) : FrameworkVW(LOGAN_SQUARE, properties)

    class Moshi(
        properties: List<AdditionalPropertiesVM> = emptyList()
    ) : FrameworkVW(MOSHI, properties)

    class AutoValue(
        properties: List<AdditionalPropertiesVM> = emptyList()
    ) : FrameworkVW(AUTO_VALUE, properties)

    class FastJson(
        properties: List<AdditionalPropertiesVM> = emptyList()
    ) : FrameworkVW(FAST_JSON, properties)

    companion object {
        const val NONE = "none"
        const val NONE_LOMBOK = "Lombok"
        const val GSON = "GSON"
        const val JACKSON = "Jackson"
        const val LOGAN_SQUARE = "Logan Square"
        const val MOSHI = "Moshi"
        const val AUTO_VALUE = "AutoValue"
        const val FAST_JSON = "FastJson"
    }
}

sealed class AdditionalPropertiesVM(
    var selected: Boolean,
    val propertyName: String
) {
    class UseSetters(
        selected: Boolean = false
    ) : AdditionalPropertiesVM(selected, SETTERS)

    class UseGetters(
        selected: Boolean = false
    ) : AdditionalPropertiesVM(selected, GETTERS)

    class UseToString(
        selected: Boolean = false
    ) : AdditionalPropertiesVM(selected, TO_STRING)

    class UseJavaPrimitives(
        selected: Boolean = false
    ) : AdditionalPropertiesVM(selected, JAVA_PRIMITIVE_TYPES)

    class UseKotlinParcelable(
        selected: Boolean = false
    ) : AdditionalPropertiesVM(selected, KOTLIN_PARCELABLE)

    class UseKotlinSingleDataClass(
        selected: Boolean = false
    ) : AdditionalPropertiesVM(selected, KOTLIN_SINGLE_DATA_CLASS)

    class UseKotlinNullableFields(
        selected: Boolean = false
    ) : AdditionalPropertiesVM(selected, KOTLIN_NULLABLE_FIELDS)

    class UseLombokValue(
        selected: Boolean = false
    ) : AdditionalPropertiesVM(selected, LOMBOK_VALUE)

    companion object {
        const val SETTERS = "create setters"
        const val GETTERS = "create getters"
        const val JAVA_PRIMITIVE_TYPES = "use Java primitive fields"
        const val TO_STRING = "override toString()"
        const val LOMBOK_VALUE = "use @Value"

        const val KOTLIN_PARCELABLE = "parcelable (Android)"
        const val KOTLIN_SINGLE_DATA_CLASS = "single data class"
        const val KOTLIN_NULLABLE_FIELDS = "nullable fields"
    }
}
