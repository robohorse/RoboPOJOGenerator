package com.robohorse.robopojogenerator.properties

import com.robohorse.robopojogenerator.properties.templates.ArrayItemsTemplate

internal class ClassField(
    private var classEnum: ClassEnum? = null,
    var className: String? = null,
    var classField: ClassField? = null
) {

    fun getJavaItem(primitive: Boolean = true): String? {
        return if (null != classField) {
            String.format(
                ArrayItemsTemplate.LIST_OF_ITEM,
                classField?.getJavaItem(primitive = false)
            )
        } else {
            className ?: if (primitive) classEnum?.primitive else classEnum?.boxed
        }
    }

    fun getKotlinItem(): String? {
        return if (null != classField) {
            String.format(
                ArrayItemsTemplate.LIST_OF_ITEM,
                classField?.getKotlinItem()
            )
        } else {
            className ?: classEnum?.kotlin
        }
    }
}
