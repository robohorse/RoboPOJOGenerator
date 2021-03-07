package com.robohorse.robopojogenerator.generator.utils

import com.robohorse.robopojogenerator.generator.consts.common.JsonModel
import com.robohorse.robopojogenerator.models.GenerationModel
import org.json.JSONArray
import org.json.JSONObject

class ProcessingModelManager {

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
