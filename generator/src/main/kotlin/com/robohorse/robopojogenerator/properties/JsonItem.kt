package com.robohorse.robopojogenerator.properties

import org.json.JSONArray
import org.json.JSONObject

internal sealed class JsonModel(
    open val key: String
) {
    data class JsonItem(
        override val key: String,
        val jsonObject: JSONObject
    ) : JsonModel(key)

    data class JsonItemArray(
        override val key: String,
        val jsonObject: JSONArray
    ) : JsonModel(key)
}
