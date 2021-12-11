package com.robohorse.robopojogenerator.generator.consts.common

internal data class ClassItem @JvmOverloads constructor(
    val className: String? = null,
    var annotation: String? = null,
    var classAnnotation: String? = null,
    var packagePath: String? = null,
    val classFields: LinkedHashMap<String, ClassField> = LinkedHashMap(),
    val classImports: HashSet<String> = HashSet()
)
