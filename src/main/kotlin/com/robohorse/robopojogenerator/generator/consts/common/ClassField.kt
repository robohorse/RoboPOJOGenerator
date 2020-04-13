package com.robohorse.robopojogenerator.generator.consts.common

import com.robohorse.robopojogenerator.generator.consts.ClassEnum
import com.robohorse.robopojogenerator.generator.consts.templates.ArrayItemsTemplate

@Deprecated("Will be refactored soon...")
data class ClassField @JvmOverloads constructor(
        var classEnum: ClassEnum? = null,
        var className: String? = null,
        var classField: ClassField? = null
) {
    fun getJavaItem(): String? {
        return if (null != classField) wrapListJava() else if (null != className) className else classEnum?.primitive
    }

    fun getKotlinItem(): String? {
        return if (null != classField) wrapListKotlin() else if (null != className) className else classEnum?.kotlin
    }

    private fun wrapListJava(): String? {
        return java.lang.String.format(ArrayItemsTemplate.LIST_OF_ITEM, classField?.getJavaBoxed())
    }

    private fun wrapListKotlin(): String? {
        return java.lang.String.format(ArrayItemsTemplate.LIST_OF_ITEM, classField?.getKotlinItem())
    }

    private fun getJavaBoxed(): String? {
        return if (null != classField) wrapListJava() else if (null != className) className else classEnum?.boxed
    }
}
