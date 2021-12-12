package com.robohorse.robopojogenerator.properties

internal data class ClassItem(
    val className: String? = null,
    var annotation: String? = null,
    var classAnnotation: String? = null,
    var packagePath: String? = null,
    val classFields: LinkedHashMap<String, ClassField> = LinkedHashMap(),
    val classImports: HashSet<String> = HashSet()
)
