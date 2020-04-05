package com.robohorse.robopojogenerator.generator.utils

import com.google.common.base.CaseFormat
import com.robohorse.robopojogenerator.errors.JSONStructureException
import com.robohorse.robopojogenerator.errors.WrongClassNameException
import com.robohorse.robopojogenerator.generator.consts.common.ClassItem
import com.robohorse.robopojogenerator.generator.consts.reserved.ReservedWords
import com.robohorse.robopojogenerator.generator.consts.templates.ArrayItemsTemplate
import com.robohorse.robopojogenerator.generator.consts.templates.ClassTemplate
import org.json.JSONArray
import org.json.JSONObject

class ClassGenerateHelper {

    fun validateJsonContent(content: String): String {
        try {
            JSONObject(content)
        } catch (exception: Exception) {
            return try {
                val jsonArray = JSONArray(content)
                if (jsonArray.length() > 0) {
                    val jsonObject = jsonArray.optJSONObject(0)
                    if (jsonObject.keySet().isEmpty()) {
                        throw JSONStructureException()
                    }
                    jsonObject.toString()
                } else {
                    throw JSONStructureException()
                }
            } catch (arrayException: Exception) {
                throw JSONStructureException()
            }
        }
        return content
    }

    fun validateClassName(name: String?) {
        if (name?.matches(NAME_PATTERN) != true) {
            throw WrongClassNameException()
        }
    }

    fun updateClassBody(classBody: String?): String? {
        if (null != classBody && classBody.isNotEmpty()) {
            val lastIndex = classBody.length - 1
            if (classBody[lastIndex] == '\n') {
                return classBody.substring(0, lastIndex)
            }
        }
        return classBody
    }

    fun formatClassName(name: String): String {
        return upperCaseFirst(proceedField(name))
    }

    fun getClassNameWithItemPostfix(name: String): String {
        return String.format(ArrayItemsTemplate.ITEM_NAME, upperCaseFirst(proceedField(name)))
    }

    fun upperCaseFirst(name: String): String {
        var name = name
        if (name.length > 1) {
            name = Character.toUpperCase(name[0]).toString() + name.substring(1)
        }
        return name
    }

    fun formatClassField(name: String): String {
        return lowerCaseFirst(proceedField(name))
    }

    fun lowerCaseFirst(name: String): String {
        var name = name
        if (name.length > 1) {
            name = Character.toLowerCase(name[0]).toString() + name.substring(1)
        }
        return name
    }

    fun setAnnotations(
            classItem: ClassItem,
            classAnnotation: String,
            annotation: String,
            imports: Array<String>) {
        classItem.classAnnotation = classAnnotation
        classItem.annotation = annotation
        for (value in imports) {
            classItem.classImports.plus(value)
        }
    }

    fun updateClassModel(classBodyBuilder: StringBuilder) {
        if (classBodyBuilder.isEmpty()) {
            classBodyBuilder.append(ClassTemplate.FIELD_KOTLIN_DOT_DEFAULT)
        } else {
            classBodyBuilder.deleteCharAt(classBodyBuilder.lastIndexOf(","))
        }
    }

    fun proceedField(objectName: String): String {
        var objectName = objectName
        objectName = objectName
                .replace("[^A-Za-z0-9]".toRegex(), "_")
                .replace("_{2,}".toRegex(), "_")
        val isDigitFirst = (objectName.length > 0 && Character.isDigit(objectName[0])
                || objectName.length > 1 && objectName[0] == '_' &&
                Character.isDigit(objectName[1]))
        if (objectName.length == 0 || isDigitFirst || ReservedWords.WORDS_SET.contains(objectName)) {
            objectName = "json_member_$objectName"
        }
        objectName = objectName.replace("([A-Z])".toRegex(), "_$1")
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, objectName)
    }
}

private val NAME_PATTERN = "^[a-zA-Z0-9]*$".toRegex()
