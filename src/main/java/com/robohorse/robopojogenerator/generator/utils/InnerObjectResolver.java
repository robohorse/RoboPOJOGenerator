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
            onSimpleObjectIdentified(ClassType.STRING.getBoxed());

        } else if (object instanceof Integer) {
            onSimpleObjectIdentified(ClassType.INTEGER.getBoxed());
            onPrimitiveObjectIdentified(ClassType.INTEGER.getPrimitive());

        } else if (object instanceof Double) {
            onSimpleObjectIdentified(ClassType.DOUBLE.getBoxed());
            onPrimitiveObjectIdentified(ClassType.DOUBLE.getPrimitive());

        } else if (object instanceof Float) {
            onSimpleObjectIdentified(ClassType.FLOAT.getBoxed());
            onPrimitiveObjectIdentified(ClassType.FLOAT.getPrimitive());

        } else if (object instanceof Long) {
            onSimpleObjectIdentified(ClassType.LONG.getBoxed());
            onPrimitiveObjectIdentified(ClassType.LONG.getPrimitive());

        } else if (object instanceof Boolean) {
            onSimpleObjectIdentified(ClassType.BOOLEAN.getBoxed());
            onPrimitiveObjectIdentified(ClassType.BOOLEAN.getPrimitive());

        } else {
            onSimpleObjectIdentified(ClassType.OBJECT.getBoxed());
        }
    }

    public abstract void onPrimitiveObjectIdentified(String classType);

    public abstract void onSimpleObjectIdentified(String classType);

    public abstract void onJsonObjectIdentified(String classType);

    public abstract void onJsonArrayIdentified(String classType);
}
