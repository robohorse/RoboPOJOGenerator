package com.robohorse.robopojogenerator.generator.common;

import org.json.JSONArray;

/**
 * Created by vadim on 22.01.17.
 */
public class JsonItemArray {
    private String key;
    private JSONArray jsonArray;

    public JsonItemArray(JSONArray jsonArray, String key) {
        this.key = key;
        this.jsonArray = jsonArray;
    }

    public String getKey() {
        return key;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }
}
