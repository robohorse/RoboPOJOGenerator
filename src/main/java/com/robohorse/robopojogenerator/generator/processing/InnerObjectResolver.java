package com.robohorse.robopojogenerator.generator.processing;

import com.robohorse.robopojogenerator.generator.consts.ClassEnum;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by vadim on 23.09.16.
 */
public abstract class InnerObjectResolver {

    public void resolveClassType(Object object) {
        if (object instanceof JSONObject) {
            onJsonObjectIdentified();

        } else if (object instanceof JSONArray) {
            onJsonArrayIdentified();

        } else if (object instanceof String) {
            onInnerObjectIdentified(ClassEnum.STRING);

        } else if (object instanceof Integer) {
            onInnerObjectIdentified(ClassEnum.INTEGER);

        } else if (object instanceof Double) {
            onInnerObjectIdentified(ClassEnum.DOUBLE);

        } else if (object instanceof Float) {
            onInnerObjectIdentified(ClassEnum.FLOAT);

        } else if (object instanceof Long) {
            onInnerObjectIdentified(ClassEnum.LONG);

        } else if (object instanceof Boolean) {
            onInnerObjectIdentified(ClassEnum.BOOLEAN);

        } else {
            onInnerObjectIdentified(ClassEnum.OBJECT);
        }
    }

    public abstract void onInnerObjectIdentified(ClassEnum classEnum);

    public abstract void onJsonObjectIdentified();

    public abstract void onJsonArrayIdentified();
}
