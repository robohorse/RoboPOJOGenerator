package com.robohorse.robopojogenerator.generator.consts;

/**
 * Created by vadim on 02.10.16.
 */
public enum ClassEnum {
    STRING("String", "String", "String"),
    INTEGER("Integer", "int", "Int"),
    BOOLEAN("Boolean", "boolean", "Boolean"),
    LONG("Long", "long", "Long"),
    FLOAT("Float", "float", "Float"),
    OBJECT("Object", "Object", "Any"),
    DOUBLE("Double", "double", "Double");

    private String primitive;
    private String boxed;
    private String kotlin;

    ClassEnum(String boxed, String primitive, String kotlin) {
        this.primitive = primitive;
        this.boxed = boxed;
        this.kotlin = kotlin;
    }

    public String getPrimitive() {
        return primitive;
    }

    public String getBoxed() {
        return boxed;
    }

    public String getKotlin() {
        return kotlin;
    }
}
