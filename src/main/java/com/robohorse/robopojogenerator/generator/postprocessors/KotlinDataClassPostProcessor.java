package com.robohorse.robopojogenerator.generator.postprocessors;

import com.robohorse.robopojogenerator.generator.common.ClassDecorator;
import com.robohorse.robopojogenerator.generator.consts.AnnotationItem;
import com.robohorse.robopojogenerator.generator.consts.ClassTemplate;
import com.robohorse.robopojogenerator.generator.consts.Imports;
import com.robohorse.robopojogenerator.generator.consts.annotations.KotlinAnnotations;
import com.robohorse.robopojogenerator.models.ClassItemModel;
import com.robohorse.robopojogenerator.models.FieldModel;
import com.robohorse.robopojogenerator.models.GenerationModel;

import javax.inject.Inject;
import java.util.Map;
import java.util.Set;

/**
 * This is the KotlinDataClassPostProcessor class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/10/24 00:30
 */
public class KotlinDataClassPostProcessor extends AbsPostProcessor {
    @Inject
    public KotlinDataClassPostProcessor() {
    }

    @Override
    protected StringBuilder proceedClassImports(ClassItemModel classItemModel) {
        final Set<String> imports = classItemModel.getClassImports();
        imports.remove(Imports.LIST);
        final StringBuilder importsBuilder = new StringBuilder();
        for (String importItem : imports) {
            importsBuilder.append(importItem.replace(";", ""));
            importsBuilder.append(ClassTemplate.NEW_LINE);
        }
        return importsBuilder;
    }

    @Override
    public String proceedClassBody(ClassItemModel classItemModel, GenerationModel generationModel) {
        final StringBuilder classBodyBuilder = new StringBuilder();
        final Map<String, ClassDecorator> classFields = classItemModel.getClassFields();
        for (String objectName : classFields.keySet()) {
            classBodyBuilder.append(classTemplateHelper.createKotlinDataClassField(
                    new FieldModel.Builder()
                            .setFieldName(objectName)
                            .setClassType(classFields.get(objectName).getKotlinItem())
                            .setAnnotation(classItemModel.getAnnotation())
                            .setFieldNameFormatted(generateHelper.formatClassField(objectName))
                            .build()
            ));
        }
        generateHelper.updateClassModel(classBodyBuilder);
        return classBodyBuilder.toString();
    }

    @Override
    protected String createClassItemText(String packagePath, String imports, String classTemplate) {
        return classTemplateHelper
                .createClassItemWithoutSemicolon(
                        packagePath,
                        imports,
                        classTemplate);
    }

    @Override
    protected void applyAnnotations(AnnotationItem item, ClassItemModel classItemModel) {
        switch (item) {
            case GSON: {
                generateHelper.setAnnotations(classItemModel,
                        KotlinAnnotations.GSON.CLASS_ANNOTATION,
                        KotlinAnnotations.GSON.ANNOTATION,
                        Imports.GSON.IMPORTS);
                break;
            }
            case LOGAN_SQUARE: {
                generateHelper.setAnnotations(classItemModel,
                        KotlinAnnotations.LOGAN_SQUARE.CLASS_ANNOTATION,
                        KotlinAnnotations.LOGAN_SQUARE.ANNOTATION,
                        Imports.LOGAN_SQUARE.IMPORTS);
                break;
            }
            case JACKSON: {
                generateHelper.setAnnotations(classItemModel,
                        KotlinAnnotations.JACKSON.CLASS_ANNOTATION,
                        KotlinAnnotations.JACKSON.ANNOTATION,
                        Imports.JACKSON.IMPORTS);
                break;
            }
        }
    }

    @Override
    public String createClassTemplate(ClassItemModel classItemModel, String classBody) {
        return classTemplateHelper.createClassBodyKotlinDataClass(classItemModel, classBody);
    }
}
