package com.robohorse.robopojogenerator.utils

import com.robohorse.robopojogenerator.properties.JsonModel
import com.robohorse.robopojogenerator.models.GenerationModel
import org.json.JSONArray
import org.json.JSONObject

internal class ProcessingModelResolver {

    fun resolveJsonModel(model: GenerationModel): JsonModel =
        try {
            JsonModel.JsonItem(jsonObject = JSONObject(model.content), key = model.rootClassName)
        } catch (e: Exception) {
            JsonModel.JsonItemArray(
                jsonObject = JSONArray(model.content),
                key = model.rootClassName
            )
        }
}
