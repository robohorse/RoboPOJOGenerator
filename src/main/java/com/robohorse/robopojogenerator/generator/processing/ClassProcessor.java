package com.robohorse.robopojogenerator.generator.processing;

import com.robohorse.robopojogenerator.generator.consts.ClassEnum;
import com.robohorse.robopojogenerator.generator.consts.common.ClassField;
import com.robohorse.robopojogenerator.generator.consts.common.ClassItem;
import com.robohorse.robopojogenerator.generator.consts.common.JsonModel;
import com.robohorse.robopojogenerator.generator.consts.common.JsonModel.JsonItem;
import com.robohorse.robopojogenerator.generator.consts.common.JsonModel.JsonItemArray;
import com.robohorse.robopojogenerator.generator.consts.templates.ImportsTemplate;
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vadim on 23.09.16.
 */
public class ClassProcessor {
    ClassGenerateHelper classGenerateHelper;

    public ClassProcessor(ClassGenerateHelper classGenerateHelper) {
        this.classGenerateHelper = classGenerateHelper;
    }

    public void proceed(JsonModel jsonItem, final Map<String, ClassItem> itemMap) {
        final ClassItem classItem = new ClassItem(classGenerateHelper.formatClassName(jsonItem.getKey()));
        if (jsonItem instanceof JsonItem) {
            processSingleObject((JsonItem) jsonItem, itemMap, classItem);
        } else {
            processArrayObject((JsonItemArray) jsonItem, itemMap, classItem);
        }
        appendItemsMap(itemMap, classItem);
    }

    private void processArrayObject(JsonItemArray arrayItem,
                                    final Map<String, ClassItem> itemMap,
                                    final ClassItem classItem) {
        final JSONArray jsonArray = arrayItem.getJsonObject();
        classItem.getClassImports().add(ImportsTemplate.LIST);

        final ClassField classField = new ClassField();
        if (jsonArray.length() == 0) {
            classField.setClassField(new ClassField(ClassEnum.OBJECT));
            classItem.getClassFields().put(arrayItem.getKey(), classField);
        } else {
            final JsonItemArray jsonItemArray = new JsonItemArray(arrayItem.getKey(), jsonArray);
            proceedArray(jsonItemArray, classField, itemMap);
            classItem.getClassFields().put(arrayItem.getKey(), classField);
        }
    }

    private void processSingleObject(JsonItem jsonItem,
                                     final Map<String, ClassItem> itemMap,
                                     final ClassItem classItem) {
        for (final String jsonObjectKey : jsonItem.getJsonObject().keySet()) {
            final Object object = jsonItem.getJsonObject().get(jsonObjectKey);
            final InnerObjectResolver innerObjectResolver = new InnerObjectResolver() {

                @Override
                public void onInnerObjectIdentified(ClassEnum classType) {
                    classItem.getClassFields().put(jsonObjectKey, new ClassField(classType));
                }

                @Override
                public void onJsonObjectIdentified() {
                    final String className = classGenerateHelper.formatClassName(jsonObjectKey);
                    final ClassField classField = new ClassField(null, className);
                    final JsonItem jsonItem = new JsonItem(jsonObjectKey, (JSONObject) object);

                    classItem.getClassFields().put(jsonObjectKey, classField);
                    proceed(jsonItem, itemMap);
                }

                @Override
                public void onJsonArrayIdentified() {
                    final JSONArray jsonArray = (JSONArray) object;
                    classItem.getClassImports().add(ImportsTemplate.LIST);

                    final ClassField classField = new ClassField();
                    if (jsonArray.length() == 0) {
                        classField.setClassField(new ClassField(ClassEnum.OBJECT));
                        classItem.getClassFields().put(jsonObjectKey, classField);

                    } else {
                        final JsonItemArray jsonItemArray = new JsonItemArray(jsonObjectKey, (JSONArray) object);
                        proceedArray(jsonItemArray, classField, itemMap);
                        classItem.getClassFields().put(jsonObjectKey, classField);
                    }
                }
            };
            innerObjectResolver.resolveClassType(object);
        }
    }

    private void appendItemsMap(Map<String, ClassItem> itemMap, ClassItem classItem) {
        final String className = classItem.getClassName();
        if (itemMap.containsKey(className)) {
            final ClassItem storedClassItem = itemMap.get(className);
            classItem.getClassFields().putAll(storedClassItem.getClassFields());
        }
        itemMap.put(className, classItem);
    }

    private void proceedArray(final JsonItemArray jsonItemArray,
                              final ClassField classField,
                              final Map<String, ClassItem> itemMap) {
        final String itemName = classGenerateHelper.getClassNameWithItemPostfix(jsonItemArray.getKey());
        if (jsonItemArray.getJsonObject().length() != 0) {
            final Object object = jsonItemArray.getJsonObject().get(0);
            final InnerObjectResolver innerObjectResolver = new InnerObjectResolver() {

                @Override
                public void onInnerObjectIdentified(ClassEnum classType) {
                    classField.setClassField(new ClassField(classType));
                }

                @Override
                public void onJsonObjectIdentified() {
                    final int size = jsonItemArray.getJsonObject().length();
                    final Map<String, ClassItem> innerItemsMap = new HashMap<String, ClassItem>();
                    for (int index = 0; index < size; index++) {
                        final JSONObject jsonObject = (JSONObject) jsonItemArray.getJsonObject().get(index);
                        final JsonItem jsonItem = new JsonItem(itemName, jsonObject);
                        proceed(jsonItem, innerItemsMap);
                    }
                    classField.setClassField(new ClassField(null, itemName));
                    innerItemsMap.forEach((key, value) -> {
                        ClassItem existing = itemMap.get(key);
                        if (existing != null) {
                            value.getClassFields().forEach((classKey, classValue) -> existing.getClassFields().putIfAbsent(classKey, classValue));
                            existing.getClassImports().addAll(value.getClassImports());
                        } else {
                            itemMap.put(key, value);
                        }
                    });
                }

                @Override
                public void onJsonArrayIdentified() {
                    classField.setClassField(new ClassField());
                    final JsonItemArray jsonItemArray = new JsonItemArray(itemName, (JSONArray) object);
                    proceedArray(jsonItemArray, classField, itemMap);
                }
            };
            innerObjectResolver.resolveClassType(object);

        } else {
            classField.setClassField(new ClassField(ClassEnum.OBJECT));
        }
    }
}
