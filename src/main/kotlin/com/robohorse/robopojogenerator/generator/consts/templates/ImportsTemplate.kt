package com.robohorse.robopojogenerator.generator.consts.templates

sealed class ImportsTemplate(
        val imports: Array<String>
) {
    object GSON : ImportsTemplate(arrayOf(SERIALIZED_NAME))

    object LOGAN_SQUARE : ImportsTemplate(arrayOf(SERIALIZED_NAME, JSON_OBJECT, JSON_FIELD))

    object JACKSON : ImportsTemplate(arrayOf(JSON_PROPERTY))

    object AUTO_VALUE_GSON : ImportsTemplate(arrayOf(SERIALIZED_NAME, AUTO_VALUE, TYPED_ADAPTER, GSON_IMPORT))

    object FAST_JSON : ImportsTemplate(arrayOf(FAST_JSON_PROPERTY))

    object MOSHI : ImportsTemplate(arrayOf(MOSHI_PROPERTY))
    companion object {
        const val LIST = "import java.util.List;"
        const val SERIALIZED_NAME = "import com.google.gson.annotations.SerializedName;"
    }
}

private const val JSON_OBJECT = "import com.bluelinelabs.logansquare.annotation.JsonObject;"
private const val JSON_FIELD = "import com.bluelinelabs.logansquare.annotation.JsonField;"
private const val JSON_PROPERTY = "import com.fasterxml.jackson.annotation.JsonProperty;"
private const val FAST_JSON_PROPERTY = "import com.alibaba.fastjson.annotation.JSONField;"
private const val MOSHI_PROPERTY = "import com.squareup.moshi.Json;"
private const val AUTO_VALUE = "import com.google.auto.value.AutoValue;"
private const val TYPED_ADAPTER = "import com.google.gson.TypeAdapter;"
private const val GSON_IMPORT = "import com.google.gson.Gson;"
