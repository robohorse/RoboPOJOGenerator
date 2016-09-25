package com.robohorse.robopojogenerator.generator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Set;
import java.util.Stack;

/**
 * Created by vadim on 23.09.16.
 */
public class ClassProcessor {

    public void proceed(JSONObject jsonObject, String className, final Set<ClassItem> classItemSet) {
        final ClassItem classItem = new ClassItem(className);
        for (final String jsonObjectKey : jsonObject.keySet()) {
            final Object object = jsonObject.get(jsonObjectKey);

            InnerObjectResolver innerObjectResolver = new InnerObjectResolver() {
                public void onSimpleObjectIdentified(String classType) {
                    classItem.addClassField(jsonObjectKey, classType);
                }

                public void onPrimitiveObjectIdentified(String classType) {
                    classItem.addClassField(jsonObjectKey, classType);
                }

                public void onJsonObjectIdentified(String classType) {
                    classItem.addClassField(jsonObjectKey, classType);
                    proceed((JSONObject) object, jsonObjectKey, classItemSet);
                }

                public void onJsonArrayIdentified(String classType) {
//                    final JSONArray jsonArray = (JSONArray) object;
//                    if (jsonArray.length() == 0) {
                    classItem.addClassField(jsonObjectKey, "List<Object>");
                    classItem.addClassImport("import java.util.List;");
//
//                    } else {
//                        Stack<String> arrayStack = new Stack<String>();
//                        arrayStack.add("List");
//                        proceedArray(jsonArray, arrayStack);
//                    }
                }
            };
            innerObjectResolver.resolveClassType(object, jsonObjectKey);
        }
        classItemSet.add(classItem);
    }

    private void proceedArray(JSONArray jsonArray, Stack<String> arrayStack) {
        Object object = jsonArray.get(0);
    }
}
