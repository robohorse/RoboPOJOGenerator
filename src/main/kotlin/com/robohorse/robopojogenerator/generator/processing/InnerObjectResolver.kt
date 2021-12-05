package com.robohorse.robopojogenerator.generator.processing

import com.robohorse.robopojogenerator.generator.consts.ClassEnum
import com.robohorse.robopojogenerator.generator.consts.ClassEnum.BOOLEAN
import com.robohorse.robopojogenerator.generator.consts.ClassEnum.DOUBLE
import com.robohorse.robopojogenerator.generator.consts.ClassEnum.FLOAT
import com.robohorse.robopojogenerator.generator.consts.ClassEnum.INTEGER
import com.robohorse.robopojogenerator.generator.consts.ClassEnum.LONG
import com.robohorse.robopojogenerator.generator.consts.ClassEnum.OBJECT
import com.robohorse.robopojogenerator.generator.consts.ClassEnum.STRING
import org.json.JSONArray
import org.json.JSONObject

internal abstract class InnerObjectResolver {

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
