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
            onInnerObjectIdentified(ClassType.STRING);

        } else if (object instanceof Integer) {
            onInnerObjectIdentified(ClassType.INTEGER);

        } else if (object instanceof Double) {
            onInnerObjectIdentified(ClassType.DOUBLE);

        } else if (object instanceof Float) {
            onInnerObjectIdentified(ClassType.FLOAT);

        } else if (object instanceof Long) {
            onInnerObjectIdentified(ClassType.LONG);

        } else if (object instanceof Boolean) {
            onInnerObjectIdentified(ClassType.BOOLEAN);

        } else {
            onInnerObjectIdentified(ClassType.OBJECT);
        }
    }

    public abstract void onInnerObjectIdentified(ClassType classType);

    public abstract void onJsonObjectIdentified(String classType);

    public abstract void onJsonArrayIdentified(String classType);
}
