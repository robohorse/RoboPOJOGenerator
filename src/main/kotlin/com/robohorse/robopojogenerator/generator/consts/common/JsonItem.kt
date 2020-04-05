package com.robohorse.robopojogenerator.generator.consts.common

import org.json.JSONArray
import org.json.JSONObject

data class JsonItem(
        val key: String,
        val jsonObject: JSONObject
)

data class JsonItemArray(
        val key: String,
        val jsonObject: JSONArray
)