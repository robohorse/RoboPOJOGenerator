package com.robohorse.robopojogenerator.generator.utils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by vadim on 23.09.16.
 */
public abstract class InnerObjectResolver {
    //TODO INJECTION
    private ClassGenerateHelper classGenerateHelper = new ClassGenerateHelper();

    public void resolveClassType(Object object, String key) {
        if (object instanceof JSONObject) {
            onJsonObjectIdentified(classGenerateHelper.getClassName(key));

        } else if (object instanceof JSONArray) {
            onJsonArrayIdentified(key);

        } else if (object instanceof String) {
            onSimpleObjectIdentified("String");

        } else if (object instanceof Integer) {
            onSimpleObjectIdentified("Integer");
            onPrimitiveObjectIdentified("int");

        } else if (object instanceof Double) {
            onSimpleObjectIdentified("Double");
            onPrimitiveObjectIdentified("double");

        } else if (object instanceof Float) {
            onSimpleObjectIdentified("Float");
            onPrimitiveObjectIdentified("float");

        } else if (object instanceof Long) {
            onSimpleObjectIdentified("Long");
            onPrimitiveObjectIdentified("long");

        } else if (object instanceof Boolean) {
            onSimpleObjectIdentified("Boolean");
            onPrimitiveObjectIdentified("boolean");

        } else {
            onSimpleObjectIdentified("Object");
        }
    }

    public abstract void onPrimitiveObjectIdentified(String classType);

    public abstract void onSimpleObjectIdentified(String classType);

    public abstract void onJsonObjectIdentified(String classType);

    public abstract void onJsonArrayIdentified(String classType);
}
