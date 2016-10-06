package com.robohorse.robopojogenerator.generator.processors;

import com.robohorse.robopojogenerator.generator.ClassItem;
import com.robohorse.robopojogenerator.generator.consts.ArrayItemsTemplate;
import com.robohorse.robopojogenerator.generator.consts.ClassType;
import com.robohorse.robopojogenerator.generator.consts.Imports;
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper;
import com.robohorse.robopojogenerator.generator.utils.InnerObjectResolver;
import com.robohorse.robopojogenerator.models.InnerArrayModel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by vadim on 23.09.16.
 */
public class ClassProcessor {
    @Inject
    ClassGenerateHelper classGenerateHelper;

    @Inject
    public ClassProcessor() {
    }

    public void proceed(JSONObject jsonObject, String className, final Set<ClassItem> classItemSet) {
        final ClassItem classItem = new ClassItem(classGenerateHelper.getClassName(className));
        for (final String jsonObjectKey : jsonObject.keySet()) {
            final Object object = jsonObject.get(jsonObjectKey);
            final InnerObjectResolver innerObjectResolver = new InnerObjectResolver() {

                @Override
                public void onPrimitiveObjectIdentified(String classType) {
                    classItem.addClassField(jsonObjectKey, classType);
                }

                @Override
                public void onJsonObjectIdentified(String classType) {
                    classItem.addClassField(jsonObjectKey, classType);
                    proceed((JSONObject) object, jsonObjectKey, classItemSet);
                }

                @Override
                public void onJsonArrayIdentified(String classType) {
                    final JSONArray jsonArray = (JSONArray) object;
                    classItem.addClassImport(Imports.LIST);

                    if (jsonArray.length() == 0) {
                        classItem.addClassField(jsonObjectKey, ArrayItemsTemplate.UNDEFINED_LIST);

                    } else {
                        final InnerArrayModel innerArrayModel = new InnerArrayModel();
                        innerArrayModel.increaseCount();

                        proceedArray(jsonArray, innerArrayModel, jsonObjectKey, classItemSet);

                        final String majorType = classGenerateHelper.resolveMajorType(innerArrayModel);
                        classItem.addClassField(jsonObjectKey, majorType);
                    }
                }
            };
            innerObjectResolver.resolveClassType(object, jsonObjectKey);
        }
        classItemSet.add(classItem);
    }

    private void proceedArray(JSONArray jsonArray, final InnerArrayModel innerArrayModel,
                              final String jsonObjectKey, final Set<ClassItem> classItemSet) {
        final String itemName = classGenerateHelper.getClassNameWithItemPostfix(jsonObjectKey);
        if (jsonArray.length() != 0) {
            final Object object = jsonArray.get(0);
            final InnerObjectResolver innerObjectResolver = new InnerObjectResolver() {

                @Override
                public void onBoxedObjectIdentified(String classType) {
                    innerArrayModel.setMajorType(classType);
                }

                @Override
                public void onJsonObjectIdentified(String classType) {
                    innerArrayModel.setMajorType(itemName);
                    proceed((JSONObject) object, itemName, classItemSet);
                }

                @Override
                public void onJsonArrayIdentified(String classType) {
                    innerArrayModel.increaseCount();
                    proceedArray((JSONArray) object, innerArrayModel, itemName, classItemSet);
                }
            };
            innerObjectResolver.resolveClassType(object, itemName);

        } else {
            innerArrayModel.increaseCount();
            innerArrayModel.setMajorType(ClassType.OBJECT.getBoxed());
        }
    }
}
