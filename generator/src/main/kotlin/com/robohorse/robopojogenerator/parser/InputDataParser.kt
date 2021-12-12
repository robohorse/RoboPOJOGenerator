package com.robohorse.robopojogenerator.parser

import com.robohorse.robopojogenerator.properties.ClassField
import com.robohorse.robopojogenerator.properties.ClassItem
import com.robohorse.robopojogenerator.properties.JsonModel
import com.robohorse.robopojogenerator.properties.JsonModel.JsonItem
import com.robohorse.robopojogenerator.properties.JsonModel.JsonItemArray
import com.robohorse.robopojogenerator.utils.ClassGenerateHelper

internal class InputDataParser(
    private val classGenerateHelper: ClassGenerateHelper,
    private val jsonObjectParser: JsonObjectParser,
    private val jsonArrayParser: JsonArrayParser
) {

    fun parse(jsonModel: JsonModel, classesMap: LinkedHashMap<String?, ClassItem>) {
        val classItem = ClassItem(classGenerateHelper.formatClassName(jsonModel.key))
        if (jsonModel is JsonItem) {
            jsonObjectParser.parseJsonObject(
                jsonModel, classesMap, classItem,
                this::parse,
                this::onArrayHandled
            )
        } else {
            jsonArrayParser.parseJsonArray(
                jsonModel as JsonItemArray,
                classesMap,
                classItem,
                this::parse
            )
        }
        appendClassesMap(classesMap, classItem)
    }

    private fun onArrayHandled(
        jsonItemArray: JsonItemArray,
        classField: ClassField,
        classesMap: LinkedHashMap<String?, ClassItem>
    ) = jsonArrayParser.parseNonEmptyArray(jsonItemArray, classField, classesMap, this::parse)

    private fun appendClassesMap(classesMap: LinkedHashMap<String?, ClassItem>, classItem: ClassItem) {
        if (classesMap.containsKey(classItem.className)) {
            classesMap[classItem.className]?.let {
                classItem.classFields.putAll(it.classFields)
            }
        }
        classesMap[classItem.className] = classItem
    }
}
