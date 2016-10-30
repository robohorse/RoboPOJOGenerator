package com.robohorse.robopojogenerator.generator.processors;

import com.robohorse.robopojogenerator.generator.common.ClassDecorator;
import com.robohorse.robopojogenerator.models.ClassItemModel;
import com.robohorse.robopojogenerator.generator.consts.ClassType;
import com.robohorse.robopojogenerator.generator.consts.Imports;
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper;
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

    public void proceed(JSONObject jsonObject, String className, final Set<ClassItemModel> classItemModelSet) {
        final ClassItemModel classItemModel = new ClassItemModel(classGenerateHelper.formatClassName(className));
        for (final String jsonObjectKey : jsonObject.keySet()) {
            final Object object = jsonObject.get(jsonObjectKey);
            final InnerObjectResolver innerObjectResolver = new InnerObjectResolver() {

                @Override
                public void onInnerObjectIdentified(ClassType classType) {
                    classItemModel.addClassField(jsonObjectKey, new ClassDecorator(classType));
                }

                @Override
                public void onJsonObjectIdentified() {
                    final String className = classGenerateHelper.formatClassName(jsonObjectKey);
                    final ClassDecorator decorator = new ClassDecorator(className);

                    classItemModel.addClassField(jsonObjectKey, decorator);
                    proceed((JSONObject) object, jsonObjectKey, classItemModelSet);
                }

                @Override
                public void onJsonArrayIdentified() {
                    final JSONArray jsonArray = (JSONArray) object;
                    classItemModel.addClassImport(Imports.LIST);

                    final ClassDecorator decorator = new ClassDecorator();
                    if (jsonArray.length() == 0) {
                        decorator.setClassDecorator(new ClassDecorator(ClassType.OBJECT));
                        classItemModel.addClassField(jsonObjectKey, decorator);

                    } else {
                        proceedArray(jsonArray, decorator, jsonObjectKey, classItemModelSet);
                        classItemModel.addClassField(jsonObjectKey, decorator);
                    }
                }
            };
            innerObjectResolver.resolveClassType(object);
        }
        classItemModelSet.add(classItemModel);
    }

    private void proceedArray(JSONArray jsonArray, final ClassDecorator decorator,
                              final String jsonObjectKey, final Set<ClassItemModel> classItemModelSet) {
        final String itemName = classGenerateHelper.getClassNameWithItemPostfix(jsonObjectKey);
        if (jsonArray.length() != 0) {
            final Object object = jsonArray.get(0);
            final InnerObjectResolver innerObjectResolver = new InnerObjectResolver() {

                @Override
                public void onInnerObjectIdentified(ClassType classType) {
                    decorator.setClassDecorator(new ClassDecorator(classType));
                }

                @Override
                public void onJsonObjectIdentified() {
                    decorator.setClassDecorator(new ClassDecorator(itemName));
                    proceed((JSONObject) object, itemName, classItemModelSet);
                }

                @Override
                public void onJsonArrayIdentified() {
                    decorator.setClassDecorator(new ClassDecorator());
                    proceedArray((JSONArray) object, decorator, itemName, classItemModelSet);
                }
            };
            innerObjectResolver.resolveClassType(object);

        } else {
            decorator.setClassDecorator(new ClassDecorator(ClassType.OBJECT));
        }
    }
}
