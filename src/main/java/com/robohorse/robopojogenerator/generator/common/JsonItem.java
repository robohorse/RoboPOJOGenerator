package com.robohorse.robopojogenerator.generator.common;

import org.json.JSONObject;

/**
 * Created by vadim on 22.01.17.
 */
public class JsonItem {
    private String key;
    private JSONObject jsonObject;

    public JsonItem(JSONObject jsonObject, String key) {
        this.key = key;
        this.jsonObject = jsonObject;
    }

    public String getKey() {
        return key;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }
}
