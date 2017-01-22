package com.robohorse.robopojogenerator.generator.processing;

import com.robohorse.robopojogenerator.generator.common.ClassField;
import com.robohorse.robopojogenerator.generator.common.ClassItem;
import com.robohorse.robopojogenerator.generator.common.JsonItem;
import com.robohorse.robopojogenerator.generator.common.JsonItemArray;
import com.robohorse.robopojogenerator.generator.consts.ClassEnum;
import com.robohorse.robopojogenerator.generator.consts.templates.ImportsTemplate;
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by vadim on 23.09.16.
 */
public class ClassProcessor {
    @Inject
    ClassGenerateHelper classGenerateHelper;

    @Inject
    public ClassProcessor() {
    }

    public void proceed(JsonItem jsonItem, final Map<String, ClassItem> itemMap) {
        final ClassItem classItem = new ClassItem(classGenerateHelper.formatClassName(jsonItem.getKey()));
        for (final String jsonObjectKey : jsonItem.getJsonObject().keySet()) {
            final Object object = jsonItem.getJsonObject().get(jsonObjectKey);
            final InnerObjectResolver innerObjectResolver = new InnerObjectResolver() {

                @Override
                public void onInnerObjectIdentified(ClassEnum classType) {
                    classItem.addClassField(jsonObjectKey, new ClassField(classType));
                }

                @Override
                public void onJsonObjectIdentified() {
                    final String className = classGenerateHelper.formatClassName(jsonObjectKey);
                    final ClassField classField = new ClassField(className);
                    final JsonItem jsonItem = new JsonItem((JSONObject) object, jsonObjectKey);

                    classItem.addClassField(jsonObjectKey, classField);
                    proceed(jsonItem, itemMap);
                }

                @Override
                public void onJsonArrayIdentified() {
                    final JSONArray jsonArray = (JSONArray) object;
                    classItem.addClassImport(ImportsTemplate.LIST);

                    final ClassField classField = new ClassField();
                    if (jsonArray.length() == 0) {
                        classField.setClassField(new ClassField(ClassEnum.OBJECT));
                        classItem.addClassField(jsonObjectKey, classField);

                    } else {
                        final JsonItemArray jsonItemArray = new JsonItemArray((JSONArray) object, jsonObjectKey);
                        proceedArray(jsonItemArray, classField, itemMap);
                        classItem.addClassField(jsonObjectKey, classField);
                    }
                }
            };
            innerObjectResolver.resolveClassType(object);
        }
        appendItemsMap(itemMap, classItem);
    }

    private void appendItemsMap(Map<String, ClassItem> itemMap, ClassItem classItem) {
        final String className = classItem.getClassName();
        if (itemMap.containsKey(className)) {
            final ClassItem storedClassItem = itemMap.get(className);
            if (storedClassItem.getClassFields().size() > classItem.getClassFields().size()) {
                return;
            }
        }
        itemMap.put(className, classItem);
    }

    private void proceedArray(final JsonItemArray jsonItemArray,
                              final ClassField classField,
                              final Map<String, ClassItem> itemMap) {
        final String itemName = classGenerateHelper.getClassNameWithItemPostfix(jsonItemArray.getKey());
        if (jsonItemArray.getJsonArray().length() != 0) {
            final Object object = jsonItemArray.getJsonArray().get(0);
            final InnerObjectResolver innerObjectResolver = new InnerObjectResolver() {

                @Override
                public void onInnerObjectIdentified(ClassEnum classType) {
                    classField.setClassField(new ClassField(classType));
                }

                @Override
                public void onJsonObjectIdentified() {
                    classField.setClassField(new ClassField(itemName));
                    final JsonItem jsonItem = new JsonItem((JSONObject) object, itemName);
                    proceed(jsonItem, itemMap);
                }

                @Override
                public void onJsonArrayIdentified() {
                    classField.setClassField(new ClassField());
                    final JsonItemArray jsonItemArray = new JsonItemArray((JSONArray) object, itemName);
                    proceedArray(jsonItemArray, classField, itemMap);
                }
            };
            innerObjectResolver.resolveClassType(object);

        } else {
            classField.setClassField(new ClassField(ClassEnum.OBJECT));
        }
    }
}
