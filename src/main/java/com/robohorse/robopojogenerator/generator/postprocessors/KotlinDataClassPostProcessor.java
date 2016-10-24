package com.robohorse.robopojogenerator.generator.postprocessors;

import com.google.common.base.CaseFormat;
import com.robohorse.robopojogenerator.generator.ClassItem;
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
        Map<String, String> classFields = classItem.getClassFields();
        for (String objectName : classFields.keySet()) {
            String type = generateHelper.updateKotlinType(classFields.get(objectName));
            String annotation = classItem.getAnnotation();
            String fieldName = proceedField(objectName);
            classBodyBuilder.append(classTemplateHelper.createKotlinDataClassField(
                    type,
                    fieldName,
                    objectName,
                    annotation));
        }
        updateClassModel(classBodyBuilder);
        return classBodyBuilder.toString();
    }

    //TODO think about this method
    private void updateClassModel(StringBuilder classBodyBuilder) {
        if (classBodyBuilder.length() == 0) {
            // Kotlin don't allow empty constructor
            classBodyBuilder.append(ClassTemplate.FIELD_KOTLIN_DOT_DEFAULT);
        } else {
            // Remove the last comma
            classBodyBuilder.deleteCharAt(classBodyBuilder.lastIndexOf(","));
        }
    }

    //TODO think about this method
    private String proceedField(String objectName) {
        String fieldName = objectName;

        if (fieldName.contains("-")) {
            // Turn to lower case with underscore if it is hyphen
            fieldName = fieldName.replaceAll("-+", "_");
        }

        if (fieldName.contains("_")) {
            fieldName = fieldName.replaceAll("_{2,}", "_");
            fieldName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, fieldName);
        }

        char fieldNameFirstChar = fieldName.charAt(0);
        if (Character.isDigit(fieldNameFirstChar)) {
            // The first char is number
            fieldName = "_" + fieldName;
        }

        return fieldName;
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
