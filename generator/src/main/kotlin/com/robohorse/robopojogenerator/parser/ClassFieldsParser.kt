package com.robohorse.robopojogenerator.parser

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

internal abstract class ClassFieldsParser {

    fun parseField(targetItem: Any) {
        when (targetItem) {
            is JSONObject -> onJsonTypeRecognised()
            is JSONArray -> onJsonArrayTypeRecognised()
            is String -> onPlainTypeRecognised(STRING)
            is Int -> onPlainTypeRecognised(INTEGER)
            is Double -> onPlainTypeRecognised(DOUBLE)
            is Float -> onPlainTypeRecognised(FLOAT)
            is Long -> onPlainTypeRecognised(LONG)
            is Boolean -> onPlainTypeRecognised(BOOLEAN)
            else -> onPlainTypeRecognised(OBJECT)
        }
    }

    abstract fun onPlainTypeRecognised(classEnum: ClassEnum?)

    abstract fun onJsonTypeRecognised()

    abstract fun onJsonArrayTypeRecognised()
}
