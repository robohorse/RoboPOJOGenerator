package com.robohorse.robopojogenerator.generator.postprocessors;

import com.robohorse.robopojogenerator.generator.ClassItem;
import com.robohorse.robopojogenerator.models.GenerationModel;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by vadim on 23.10.16.
 */
public class AutoValueClassPostProcessor extends AbsPostProcessor {

    @Inject
    public AutoValueClassPostProcessor() {
    }

    @Override
    public String proceedClassBody(ClassItem classItem, GenerationModel generationModel) {
        final StringBuilder classBodyBuilder = new StringBuilder();
        final StringBuilder classMethodBuilder = new StringBuilder();
        final Map<String, String> classFields = classItem.getClassFields();
        for (String objectName : classFields.keySet()) {
            classBodyBuilder.append(classTemplateProcessor
                    .createFiled(classFields.get(objectName), objectName, classItem.getAnnotation()));

        }
        classBodyBuilder.append(classMethodBuilder);
        return classBodyBuilder.toString();
    }
}
