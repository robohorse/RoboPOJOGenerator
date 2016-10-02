package com.robohorse.robopojogenerator.generator.consts;

/**
 * Created by vadim on 02.10.16.
 */
public enum ClassType {
    STRING("String", "String"),
    INTEGER("Integer", "int"),
    BOOLEAN("Boolean", "boolean"),
    LONG("Long", "long"),
    FLOAT("Float", "float"),
    OBJECT("Object", "Object"),
    DOUBLE("Double", "double");

    private String primitive;
    private String boxed;

    ClassType(String boxed, String primitive) {
        this.primitive = primitive;
        this.boxed = boxed;
    }

    public String getPrimitive() {
        return primitive;
    }

    public String getBoxed() {
        return boxed;
    }
}
