package com.robohorse.robopojogenerator.generator.postprocessors;

import com.robohorse.robopojogenerator.generator.common.ClassDecorator;
import com.robohorse.robopojogenerator.generator.common.ClassItem;
import com.robohorse.robopojogenerator.generator.consts.AnnotationItem;
import com.robohorse.robopojogenerator.generator.consts.ClassTemplate;
import com.robohorse.robopojogenerator.generator.consts.Imports;
import com.robohorse.robopojogenerator.generator.consts.annotations.KotlinAnnotations;
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
    protected StringBuilder proceedClassImports(ClassItem classItem) {
        final Set<String> imports = classItem.getClassImports();
        imports.remove(Imports.LIST);
        final StringBuilder importsBuilder = new StringBuilder();
        for (String importItem : imports) {
            importsBuilder.append(importItem.replace(";", ""));
            importsBuilder.append(ClassTemplate.NEW_LINE);
        }
        return importsBuilder;
    }

    @Override
    public String proceedClassBody(ClassItem classItem, GenerationModel generationModel) {
        final StringBuilder classBodyBuilder = new StringBuilder();
        final Map<String, ClassDecorator> classFields = classItem.getClassFields();
        for (String objectName : classFields.keySet()) {
            final String type = classFields.get(objectName).getKotlinItem();
            final String annotation = classItem.getAnnotation();
            final String itemNameFormatted = generateHelper.formatClassField(objectName);
            classBodyBuilder.append(classTemplateHelper.createKotlinDataClassField(
                    type,
                    itemNameFormatted,
                    annotation));
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
    protected void applyAnnotations(AnnotationItem item, ClassItem classItem) {
        switch (item) {
            case GSON: {
                generateHelper.setAnnotations(classItem,
                        KotlinAnnotations.GSON.CLASS_ANNOTATION,
                        KotlinAnnotations.GSON.ANNOTATION,
                        Imports.GSON.IMPORTS);
                break;
            }
            case LOGAN_SQUARE: {
                generateHelper.setAnnotations(classItem,
                        KotlinAnnotations.LOGAN_SQUARE.CLASS_ANNOTATION,
                        KotlinAnnotations.LOGAN_SQUARE.ANNOTATION,
                        Imports.LOGAN_SQUARE.IMPORTS);
                break;
            }
            case JACKSON: {
                generateHelper.setAnnotations(classItem,
                        KotlinAnnotations.JACKSON.CLASS_ANNOTATION,
                        KotlinAnnotations.JACKSON.ANNOTATION,
                        Imports.JACKSON.IMPORTS);
                break;
            }
        }
    }

    @Override
    public String createClassTemplate(ClassItem classItem, String classBody) {
        return classTemplateHelper.createClassBodyKotlinDataClass(classItem, classBody);
    }
}
