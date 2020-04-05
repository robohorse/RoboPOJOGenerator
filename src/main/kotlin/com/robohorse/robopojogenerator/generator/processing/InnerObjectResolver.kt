package com.robohorse.robopojogenerator.generator.processing

import com.robohorse.robopojogenerator.generator.consts.ClassEnum
import com.robohorse.robopojogenerator.generator.consts.ClassEnum.*
import org.json.JSONArray
import org.json.JSONObject

abstract class InnerObjectResolver {

    fun resolveClassType(targetItem: Any) {
        when (targetItem) {
            is JSONObject -> onJsonObjectIdentified()
            is JSONArray -> onJsonArrayIdentified()
            is String -> onInnerObjectIdentified(STRING)
            is Int -> onInnerObjectIdentified(INTEGER)
            is Double -> onInnerObjectIdentified(DOUBLE)
            is Float -> onInnerObjectIdentified(FLOAT)
            is Long -> onInnerObjectIdentified(LONG)
            is Boolean -> onInnerObjectIdentified(BOOLEAN)
            else -> onInnerObjectIdentified(OBJECT)
        }
    }

    abstract fun onInnerObjectIdentified(classEnum: ClassEnum?)

    abstract fun onJsonObjectIdentified()

    abstract fun onJsonArrayIdentified()
}
