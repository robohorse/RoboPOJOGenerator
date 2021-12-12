package com.robohorse.robopojogenerator.processing

import com.robohorse.robopojogenerator.properties.ClassEnum
import com.robohorse.robopojogenerator.properties.ClassEnum.BOOLEAN
import com.robohorse.robopojogenerator.properties.ClassEnum.DOUBLE
import com.robohorse.robopojogenerator.properties.ClassEnum.FLOAT
import com.robohorse.robopojogenerator.properties.ClassEnum.INTEGER
import com.robohorse.robopojogenerator.properties.ClassEnum.LONG
import com.robohorse.robopojogenerator.properties.ClassEnum.OBJECT
import com.robohorse.robopojogenerator.properties.ClassEnum.STRING
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
