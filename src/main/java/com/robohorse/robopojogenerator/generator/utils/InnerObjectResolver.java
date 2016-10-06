package com.robohorse.robopojogenerator.generator.utils;

import com.robohorse.robopojogenerator.generator.consts.ClassType;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by vadim on 23.09.16.
 */
public abstract class InnerObjectResolver {

    private ClassGenerateHelper classGenerateHelper = new ClassGenerateHelper();

    public void resolveClassType(Object object, String key) {
        if (object instanceof JSONObject) {
            onJsonObjectIdentified(classGenerateHelper.getClassName(key));

        } else if (object instanceof JSONArray) {
            onJsonArrayIdentified(key);

        } else if (object instanceof String) {
            onBoxedObjectIdentified(ClassType.STRING.getBoxed());
            onPrimitiveObjectIdentified(ClassType.STRING.getPrimitive());

        } else if (object instanceof Integer) {
            onBoxedObjectIdentified(ClassType.INTEGER.getBoxed());
            onPrimitiveObjectIdentified(ClassType.INTEGER.getPrimitive());

        } else if (object instanceof Double) {
            onBoxedObjectIdentified(ClassType.DOUBLE.getBoxed());
            onPrimitiveObjectIdentified(ClassType.DOUBLE.getPrimitive());

        } else if (object instanceof Float) {
            onBoxedObjectIdentified(ClassType.FLOAT.getBoxed());
            onPrimitiveObjectIdentified(ClassType.FLOAT.getPrimitive());

        } else if (object instanceof Long) {
            onBoxedObjectIdentified(ClassType.LONG.getBoxed());
            onPrimitiveObjectIdentified(ClassType.LONG.getPrimitive());

        } else if (object instanceof Boolean) {
            onBoxedObjectIdentified(ClassType.BOOLEAN.getBoxed());
            onPrimitiveObjectIdentified(ClassType.BOOLEAN.getPrimitive());

        } else {
            onBoxedObjectIdentified(ClassType.OBJECT.getBoxed());
            onPrimitiveObjectIdentified(ClassType.OBJECT.getPrimitive());
        }
    }

    public void onPrimitiveObjectIdentified(String classType) {
    }

    public void onBoxedObjectIdentified(String classType) {
    }

    public abstract void onJsonObjectIdentified(String classType);

    public abstract void onJsonArrayIdentified(String classType);
}
