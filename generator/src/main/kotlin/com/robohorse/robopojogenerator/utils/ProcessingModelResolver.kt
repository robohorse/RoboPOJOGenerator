package com.robohorse.robopojogenerator.utils

import com.robohorse.robopojogenerator.properties.JsonModel
import com.robohorse.robopojogenerator.models.GenerationModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

internal class ProcessingModelResolver {

    fun resolveJsonModel(model: GenerationModel): JsonModel {
        val content = model.content.trim()
        return when (content.firstOrNull()) {
            '{' -> try {
                JsonModel.JsonItem(jsonObject = JSONObject(content), key = model.rootClassName)
            } catch (e: JSONException) {
                throw IllegalArgumentException("Invalid JSON object: ${e.message}", e)
            }
            '[' -> try {
                JsonModel.JsonItemArray(jsonObject = JSONArray(content), key = model.rootClassName)
            } catch (e: JSONException) {
                throw IllegalArgumentException("Invalid JSON array: ${e.message}", e)
            }
            else -> throw IllegalArgumentException(
                "Content must be a JSON object or array, got: ${content.take(50)}"
            )
        }
    }
}
