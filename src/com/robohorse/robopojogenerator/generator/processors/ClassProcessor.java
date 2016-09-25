package com.robohorse.robopojogenerator.generator.processors;

import com.robohorse.robopojogenerator.generator.ClassItem;
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper;
import com.robohorse.robopojogenerator.generator.utils.InnerObjectResolver;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Set;
import java.util.Stack;

/**
 * Created by vadim on 23.09.16.
 */
public class ClassProcessor {
    private ClassGenerateHelper classGenerateHelper = new ClassGenerateHelper();

    public void proceed(JSONObject jsonObject, String className, final Set<ClassItem> classItemSet) {
        final ClassItem classItem = new ClassItem(classGenerateHelper.getClassName(className));
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
                    final JSONArray jsonArray = (JSONArray) object;
                    classItem.addClassImport("import java.util.List;");

                    if (jsonArray.length() == 0) {
                        classItem.addClassField(jsonObjectKey, "List<Object>");

                    } else {
                        Stack<String> arrayStack = new Stack<>();
                        arrayStack.add("List");
                        proceedArray(jsonArray, arrayStack, jsonObjectKey, classItemSet);

                        String majorType = arrayStack.pop();
                        final int size = arrayStack.size();
                        for (int i = 0; i < size; i++) {
                            majorType = "List<" + majorType + ">";
                        }
                        classItem.addClassField(jsonObjectKey, majorType);
                    }
                }
            };
            innerObjectResolver.resolveClassType(object, jsonObjectKey);
        }
        classItemSet.add(classItem);
    }

    private void proceedArray(JSONArray jsonArray, Stack<String> arrayStack, String jsonObjectKey,
                              Set<ClassItem> classItemSet) {
        final String itemName = classGenerateHelper.getClassName(jsonObjectKey) + "Item";
        if (jsonArray.length() != 0) {
            Object object = jsonArray.get(0);
            InnerObjectResolver innerObjectResolver = new InnerObjectResolver() {
                @Override
                public void onPrimitiveObjectIdentified(String classType) {

                }

                @Override
                public void onSimpleObjectIdentified(String classType) {
                    arrayStack.add(classType);
                }

                @Override
                public void onJsonObjectIdentified(String classType) {
                    arrayStack.add(itemName);
                    proceed((JSONObject) object, itemName, classItemSet);
                }

                @Override
                public void onJsonArrayIdentified(String classType) {
                    arrayStack.add("List");
                    proceedArray((JSONArray) object, arrayStack, itemName, classItemSet);
                }
            };
            innerObjectResolver.resolveClassType(object, itemName);

        } else {
            arrayStack.add("List<Object>");
        }
    }
}
