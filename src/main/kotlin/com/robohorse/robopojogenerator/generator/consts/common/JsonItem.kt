package com.robohorse.robopojogenerator.generator.consts.common

import org.json.JSONArray
import org.json.JSONObject

sealed class JsonModel(
    open val key: String,
    val keySet:Set<String>
) {
    data class JsonItem(
        override val key: String,
        val jsonObject: JSONObject
    ) : JsonModel(key, jsonObject.keySet())

    data class JsonItemArray(
        override val key: String,
        val jsonObject: JSONArray
    ) : JsonModel(key, emptySet())
}
