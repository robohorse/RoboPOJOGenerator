package com.robohorse.robopojogenerator.generator.processing;

import com.robohorse.robopojogenerator.generator.common.ClassField;
import com.robohorse.robopojogenerator.generator.common.ClassItem;
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

    public void proceed(JSONObject jsonObject, String className, final Map<String, ClassItem> itemMap) {
        final ClassItem classItem = new ClassItem(classGenerateHelper.formatClassName(className));
        for (final String jsonObjectKey : jsonObject.keySet()) {
            final Object object = jsonObject.get(jsonObjectKey);
            final InnerObjectResolver innerObjectResolver = new InnerObjectResolver() {

                @Override
                public void onInnerObjectIdentified(ClassEnum classType) {
                    classItem.addClassField(jsonObjectKey, new ClassField(classType));
                }

                @Override
                public void onJsonObjectIdentified() {
                    final String className = classGenerateHelper.formatClassName(jsonObjectKey);
                    final ClassField classField = new ClassField(className);

                    classItem.addClassField(jsonObjectKey, classField);
                    proceed((JSONObject) object, jsonObjectKey, itemMap);
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
                        proceedArray(jsonArray, classField, jsonObjectKey, itemMap);
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

    private void proceedArray(JSONArray jsonArray, final ClassField classField,
                              final String jsonObjectKey, final Map<String, ClassItem> itemMap) {
        final String itemName = classGenerateHelper.getClassNameWithItemPostfix(jsonObjectKey);
        if (jsonArray.length() != 0) {
            final Object object = jsonArray.get(0);
            final InnerObjectResolver innerObjectResolver = new InnerObjectResolver() {

                @Override
                public void onInnerObjectIdentified(ClassEnum classType) {
                    classField.setClassField(new ClassField(classType));
                }

                @Override
                public void onJsonObjectIdentified() {
                    classField.setClassField(new ClassField(itemName));
                    proceed((JSONObject) object, itemName, itemMap);
                }

                @Override
                public void onJsonArrayIdentified() {
                    classField.setClassField(new ClassField());
                    proceedArray((JSONArray) object, classField, itemName, itemMap);
                }
            };
            innerObjectResolver.resolveClassType(object);

        } else {
            classField.setClassField(new ClassField(ClassEnum.OBJECT));
        }
    }
}
