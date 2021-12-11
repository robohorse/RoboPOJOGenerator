package com.robohorse.robopojogenerator.generator.processing

import com.robohorse.robopojogenerator.generator.common.ClassEnum
import com.robohorse.robopojogenerator.generator.common.ClassEnum.BOOLEAN
import com.robohorse.robopojogenerator.generator.common.ClassEnum.DOUBLE
import com.robohorse.robopojogenerator.generator.common.ClassEnum.FLOAT
import com.robohorse.robopojogenerator.generator.common.ClassEnum.INTEGER
import com.robohorse.robopojogenerator.generator.common.ClassEnum.LONG
import com.robohorse.robopojogenerator.generator.common.ClassEnum.OBJECT
import com.robohorse.robopojogenerator.generator.common.ClassEnum.STRING
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
