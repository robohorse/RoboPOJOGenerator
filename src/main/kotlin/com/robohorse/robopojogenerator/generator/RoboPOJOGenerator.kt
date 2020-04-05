package com.robohorse.robopojogenerator.generator

import com.robohorse.robopojogenerator.generator.consts.common.ClassItem
import com.robohorse.robopojogenerator.generator.consts.common.JsonItem
import com.robohorse.robopojogenerator.generator.processing.ClassProcessor
import com.robohorse.robopojogenerator.models.GenerationModel
import org.json.JSONObject
import java.util.*

class RoboPOJOGenerator(
        private val processor: ClassProcessor
) {

    fun generate(model: GenerationModel): Set<ClassItem> {
        val map = HashMap<String, ClassItem>()
        processor.proceed(
                JsonItem(jsonObject = JSONObject(model.content), key = model.rootClassName),
                map
        )
        return HashSet(map.values)
    }
}
