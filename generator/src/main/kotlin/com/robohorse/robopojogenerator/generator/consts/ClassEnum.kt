package com.robohorse.robopojogenerator.generator.consts

internal enum class ClassEnum(
    val boxed: String,
    val primitive: String,
    val kotlin: String
) {
    STRING("String", "String", "String"),
    INTEGER("Integer", "int", "Int"),
    BOOLEAN("Boolean", "boolean", "Boolean"),
    LONG("Long", "long", "Long"),
    FLOAT("Float", "float", "Float"),
    OBJECT("Object", "Object", "Any"),
    DOUBLE("Double", "double", "Double");
}
