package com.robohorse.robopojogenerator.generator.consts.templates

internal sealed class ImportsTemplate(
    val imports: Array<String>
) {
    object GSON : ImportsTemplate(arrayOf(SERIALIZED_NAME))

    object LOGAN_SQUARE : ImportsTemplate(arrayOf(JSON_OBJECT, JSON_FIELD))

    object JACKSON : ImportsTemplate(arrayOf(JSON_PROPERTY))

    object AUTO_VALUE_GSON : ImportsTemplate(arrayOf(SERIALIZED_NAME, AUTO_VALUE, TYPED_ADAPTER, GSON_IMPORT))

    object FAST_JSON : ImportsTemplate(arrayOf(FAST_JSON_PROPERTY))

    object MOSHI : ImportsTemplate(arrayOf(MOSHI_PROPERTY))

    class Lombok(useValue: Boolean) : ImportsTemplate(
        imports = if (useValue) arrayOf(LOMBOK_VALUE) else arrayOf(LOMBOK_DATA)
    )

    companion object {
        const val LIST = "import java.util.List;"
        const val SERIALIZED_NAME = "import com.google.gson.annotations.SerializedName;"
    }
}

const val JSON_OBJECT = "import com.bluelinelabs.logansquare.annotation.JsonObject;"
const val JSON_FIELD = "import com.bluelinelabs.logansquare.annotation.JsonField;"
const val JSON_PROPERTY = "import com.fasterxml.jackson.annotation.JsonProperty;"
const val FAST_JSON_PROPERTY = "import com.alibaba.fastjson.annotation.JSONField;"
const val MOSHI_PROPERTY = "import com.squareup.moshi.Json;"
const val LOMBOK_DATA = "import lombok.Data;"
const val LOMBOK_VALUE = "import lombok.Value;"
const val AUTO_VALUE = "import com.google.auto.value.AutoValue;"
const val TYPED_ADAPTER = "import com.google.gson.TypeAdapter;"
const val GSON_IMPORT = "import com.google.gson.Gson;"

const val PARCELABLE_ANDROID = "import android.os.Parcelable"
const val PARCELIZE_KOTLINX = "import kotlinx.android.parcel.Parcelize"
