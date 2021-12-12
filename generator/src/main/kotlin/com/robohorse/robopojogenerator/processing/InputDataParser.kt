package com.robohorse.robopojogenerator.processing

import com.robohorse.robopojogenerator.properties.ClassEnum
import com.robohorse.robopojogenerator.properties.ClassField
import com.robohorse.robopojogenerator.properties.ClassItem
import com.robohorse.robopojogenerator.properties.JsonModel
import com.robohorse.robopojogenerator.properties.JsonModel.JsonItemArray
import com.robohorse.robopojogenerator.properties.templates.ImportsTemplate
import com.robohorse.robopojogenerator.utils.ClassGenerateHelper
import org.json.JSONArray
import org.json.JSONObject

internal class InputDataParser(
    private val classGenerateHelper: ClassGenerateHelper
) {

    fun parse(jsonModel: JsonModel, classesMap: LinkedHashMap<String?, ClassItem>) {
        val classItem = ClassItem(classGenerateHelper.formatClassName(jsonModel.key))
        if (jsonModel is JsonModel.JsonItem) {
            parseJsonObject(jsonModel, classesMap, classItem)
        } else {
            parseJsonArray(jsonModel as JsonItemArray, classesMap, classItem)
        }
        appendClassesMap(classesMap, classItem)
    }

    private fun parseJsonArray(
        jsonItemArray: JsonItemArray,
        classesMap: LinkedHashMap<String?, ClassItem>,
        classItem: ClassItem
    ) {
        val jsonArray = jsonItemArray.jsonObject
        classItem.classImports.add(ImportsTemplate.LIST)
        val classField = ClassField()
        if (jsonArray.length() == 0) {
            classField.classField = ClassField(ClassEnum.OBJECT)
            classItem.classFields[jsonItemArray.key] = classField
        } else {
            proceedArray(JsonItemArray(jsonItemArray.key, jsonArray), classField, classesMap)
            classItem.classFields[jsonItemArray.key] = classField
        }
    }

    private fun parseJsonObject(
        jsonItem: JsonModel.JsonItem,
        classesMap: LinkedHashMap<String?, ClassItem>,
        classItem: ClassItem
    ) {
        for (jsonObjectKey in jsonItem.jsonObject.keySet()) {
            val itemObject = jsonItem.jsonObject[jsonObjectKey]
            val classFieldsParser = object : ClassFieldsParser() {

                override fun onPlainTypeRecognised(classEnum: ClassEnum?) {
                    classItem.classFields[jsonObjectKey] = ClassField(classEnum)
                }

                override fun onJsonTypeRecognised() {
                    val className = classGenerateHelper.formatClassName(jsonObjectKey)
                    val classField = ClassField(null, className)
                    val innerJsonItem = JsonModel.JsonItem(jsonObjectKey, (itemObject as JSONObject))
                    classItem.classFields[jsonObjectKey] = classField
                    parse(innerJsonItem, classesMap)
                }

                override fun onJsonArrayTypeRecognised() {
                    val jsonArray = itemObject as JSONArray
                    classItem.classImports.add(ImportsTemplate.LIST)
                    val classField = ClassField()
                    if (jsonArray.length() == 0) {
                        classField.classField = ClassField(ClassEnum.OBJECT)
                        classItem.classFields[jsonObjectKey] = classField
                    } else {
                        val jsonItemArray = JsonItemArray(jsonObjectKey, itemObject)
                        proceedArray(jsonItemArray, classField, classesMap)
                        classItem.classFields[jsonObjectKey] = classField
                    }
                }
            }
            classFieldsParser.parseField(itemObject)
        }
    }

    private fun appendClassesMap(classesMap: LinkedHashMap<String?, ClassItem>, classItem: ClassItem) {
        if (classesMap.containsKey(classItem.className)) {
            classesMap[classItem.className]?.let {
                classItem.classFields.putAll(it.classFields)
            }
        }
        classesMap[classItem.className] = classItem
    }

    private fun proceedArray(
        jsonItemArray: JsonItemArray,
        classField: ClassField,
        classesMap: LinkedHashMap<String?, ClassItem>
    ) {
        val itemName = classGenerateHelper.getClassNameWithItemPostfix(jsonItemArray.key)
        if (jsonItemArray.jsonObject.length() != 0) {
            val itemObject = jsonItemArray.jsonObject.first()
            val classFieldsParser = object : ClassFieldsParser() {

                override fun onPlainTypeRecognised(classEnum: ClassEnum?) {
                    classField.classField = ClassField(classEnum)
                }

                override fun onJsonTypeRecognised() {
                    val size = jsonItemArray.jsonObject.length()
                    val innerItemsMap = LinkedHashMap<String?, ClassItem>()
                    for (index in 0 until size) {
                        val jsonObject = jsonItemArray.jsonObject[index] as JSONObject
                        val jsonItem = JsonModel.JsonItem(itemName, jsonObject)
                        parse(jsonItem, innerItemsMap)
                    }
                    classField.classField = ClassField(null, itemName)
                    innerItemsMap.forEach { (key: String?, value: ClassItem) ->
                        val existing = classesMap[key]
                        if (existing != null) {
                            value.classFields.forEach { (classKey: String?, classValue: ClassField?) ->
                                existing.classFields.putIfAbsent(classKey, classValue)
                            }
                            existing.classImports.addAll(value.classImports)
                        } else {
                            classesMap[key] = value
                        }
                    }
                }

                override fun onJsonArrayTypeRecognised() {
                    classField.classField = ClassField()
                    proceedArray(JsonItemArray(itemName, (itemObject as JSONArray)), classField, classesMap)
                }
            }
            classFieldsParser.parseField(itemObject)
        } else {
            classField.classField = ClassField(ClassEnum.OBJECT)
        }
    }
}
