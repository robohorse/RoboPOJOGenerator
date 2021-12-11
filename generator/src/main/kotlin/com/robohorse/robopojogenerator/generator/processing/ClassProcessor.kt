package com.robohorse.robopojogenerator.generator.processing

import com.robohorse.robopojogenerator.generator.common.ClassEnum
import com.robohorse.robopojogenerator.generator.common.common.ClassField
import com.robohorse.robopojogenerator.generator.common.common.ClassItem
import com.robohorse.robopojogenerator.generator.common.common.JsonModel
import com.robohorse.robopojogenerator.generator.common.common.JsonModel.JsonItemArray
import com.robohorse.robopojogenerator.generator.common.templates.ImportsTemplate
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper
import org.json.JSONArray
import org.json.JSONObject

internal class ClassProcessor(
    private val classGenerateHelper: ClassGenerateHelper
) {

    fun proceed(jsonItem: JsonModel, itemMap: LinkedHashMap<String?, ClassItem>) {
        val classItem = ClassItem(classGenerateHelper.formatClassName(jsonItem.key))
        if (jsonItem is JsonModel.JsonItem) {
            processSingleObject(jsonItem, itemMap, classItem)
        } else {
            processArrayObject(jsonItem as JsonItemArray, itemMap, classItem)
        }
        appendItemsMap(itemMap, classItem)
    }

    private fun processArrayObject(
        arrayItem: JsonItemArray,
        itemMap: MutableMap<String?, ClassItem>,
        classItem: ClassItem
    ) {
        val jsonArray = arrayItem.jsonObject
        classItem.classImports.add(ImportsTemplate.LIST)
        val classField = ClassField()
        if (jsonArray.length() == 0) {
            classField.classField = ClassField(ClassEnum.OBJECT)
            classItem.classFields.put(arrayItem.key, classField)
        } else {
            val jsonItemArray = JsonItemArray(arrayItem.key, jsonArray)
            proceedArray(jsonItemArray, classField, itemMap)
            classItem.classFields.put(arrayItem.key, classField)
        }
    }

    private fun processSingleObject(
        jsonItem: JsonModel.JsonItem,
        itemMap: LinkedHashMap<String?, ClassItem>,
        classItem: ClassItem
    ) {
        for (jsonObjectKey in jsonItem.jsonObject.keySet()) {
            val `object` = jsonItem.jsonObject[jsonObjectKey]
            val innerObjectResolver: InnerObjectResolver = object : InnerObjectResolver() {
                override fun onInnerObjectIdentified(classType: ClassEnum?) {
                    classItem.classFields.put(jsonObjectKey, ClassField(classType))
                }

                override fun onJsonObjectIdentified() {
                    val className = classGenerateHelper!!.formatClassName(jsonObjectKey)
                    val classField = ClassField(null, className)
                    val jsonItem = JsonModel.JsonItem(jsonObjectKey, (`object` as JSONObject))
                    classItem.classFields.put(jsonObjectKey, classField)
                    proceed(jsonItem, itemMap)
                }

                override fun onJsonArrayIdentified() {
                    val jsonArray = `object` as JSONArray
                    classItem.classImports.add(ImportsTemplate.LIST)
                    val classField = ClassField()
                    if (jsonArray.length() == 0) {
                        classField.classField = ClassField(ClassEnum.OBJECT)
                        classItem.classFields.put(jsonObjectKey, classField)
                    } else {
                        val jsonItemArray = JsonItemArray(
                            jsonObjectKey,
                            `object`
                        )
                        proceedArray(jsonItemArray, classField, itemMap)
                        classItem.classFields.put(jsonObjectKey, classField)
                    }
                }
            }
            innerObjectResolver.resolveClassType(`object`)
        }
    }

    private fun appendItemsMap(itemMap: MutableMap<String?, ClassItem>, classItem: ClassItem) {
        val className = classItem.className
        if (itemMap.containsKey(className)) {
            val storedClassItem = itemMap[className]
            classItem.classFields.putAll(storedClassItem!!.classFields)
        }
        itemMap[className] = classItem
    }

    private fun proceedArray(
        jsonItemArray: JsonItemArray,
        classField: ClassField,
        itemMap: MutableMap<String?, ClassItem>
    ) {
        val itemName = classGenerateHelper!!.getClassNameWithItemPostfix(jsonItemArray.key)
        if (jsonItemArray.jsonObject.length() != 0) {
            val `object` = jsonItemArray.jsonObject[0]
            val innerObjectResolver: InnerObjectResolver = object : InnerObjectResolver() {
                override fun onInnerObjectIdentified(classType: ClassEnum?) {
                    classField.classField = ClassField(classType)
                }

                override fun onJsonObjectIdentified() {
                    val size = jsonItemArray.jsonObject.length()
                    val innerItemsMap: LinkedHashMap<String?, ClassItem> = LinkedHashMap()
                    for (index in 0 until size) {
                        val jsonObject = jsonItemArray.jsonObject[index] as JSONObject
                        val jsonItem = JsonModel.JsonItem(itemName, jsonObject)
                        proceed(jsonItem, innerItemsMap)
                    }
                    classField.classField = ClassField(null, itemName)
                    innerItemsMap.forEach { (key: String?, value: ClassItem) ->
                        val existing = itemMap[key]
                        if (existing != null) {
                            value.classFields.forEach { (classKey: String?, classValue: ClassField?) ->
                                existing.classFields.putIfAbsent(
                                    classKey,
                                    classValue
                                )
                            }
                            existing.classImports.addAll(value.classImports)
                        } else {
                            itemMap[key] = value
                        }
                    }
                }

                override fun onJsonArrayIdentified() {
                    classField.classField = ClassField()
                    val jsonItemArray = JsonItemArray(itemName, (`object` as JSONArray))
                    proceedArray(jsonItemArray, classField, itemMap)
                }
            }
            innerObjectResolver.resolveClassType(`object`)
        } else {
            classField.classField = ClassField(ClassEnum.OBJECT)
        }
    }
}