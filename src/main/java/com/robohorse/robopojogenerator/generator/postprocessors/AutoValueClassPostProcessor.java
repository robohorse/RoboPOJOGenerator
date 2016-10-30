package com.robohorse.robopojogenerator.generator.postprocessors;

import com.robohorse.robopojogenerator.generator.common.ClassDecorator;
import com.robohorse.robopojogenerator.generator.consts.ClassTemplate;
import com.robohorse.robopojogenerator.models.ClassItemModel;
import com.robohorse.robopojogenerator.models.FieldModel;
import com.robohorse.robopojogenerator.models.GenerationModel;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by vadim on 23.10.16.
 */
public class AutoValueClassPostProcessor extends JavaPostProcessor {
    @Inject
    public AutoValueClassPostProcessor() {
    }

    @Override
    public String proceedClassBody(ClassItemModel classItemModel, GenerationModel generationModel) {
        final StringBuilder classBodyBuilder = new StringBuilder();
        final Map<String, ClassDecorator> classFields = classItemModel.getClassFields();
        for (String objectName : classFields.keySet()) {
            classBodyBuilder.append(classTemplateHelper.createAutoValueField(
                    new FieldModel.Builder()
                            .setClassType(classFields.get(objectName).getJavaItem())
                            .setAnnotation(classItemModel.getAnnotation())
                            .setFieldName(objectName)
                            .setFieldNameFormatted(generateHelper.formatClassField(objectName))
                            .build())
            );
        }
        classBodyBuilder.append(ClassTemplate.NEW_LINE);
        classBodyBuilder.append(classTemplateHelper.createTypeAdapter(classItemModel));
        return classBodyBuilder.toString();
    }

    @Override
    public String createClassTemplate(ClassItemModel classItemModel, String classBody) {
        return classTemplateHelper.createClassBodyAbstract(classItemModel, classBody);
    }
}
