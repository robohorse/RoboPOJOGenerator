package com.robohorse.robopojogenerator.utils

import com.robohorse.robopojogenerator.properties.JsonModel
import com.robohorse.robopojogenerator.models.GenerationModel
import org.json.JSONArray
import org.json.JSONObject

internal class ProcessingModelResolver {

    fun resolveJsonModel(model: GenerationModel): JsonModel {
        val content = model.content.trim()
        return when (content.firstOrNull()) {
            '{' -> JsonModel.JsonItem(jsonObject = JSONObject(content), key = model.rootClassName)
            '[' -> JsonModel.JsonItemArray(jsonObject = JSONArray(content), key = model.rootClassName)
            else -> throw IllegalArgumentException(
                "Content must be a JSON object or array, got: ${content.take(50)}"
            )
        }
    }
}
