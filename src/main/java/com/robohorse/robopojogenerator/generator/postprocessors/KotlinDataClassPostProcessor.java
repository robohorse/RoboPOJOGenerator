package com.robohorse.robopojogenerator.generator.postprocessors;

import com.google.common.base.CaseFormat;
import com.robohorse.robopojogenerator.generator.ClassItem;
import com.robohorse.robopojogenerator.generator.consts.ClassTemplate;
import com.robohorse.robopojogenerator.generator.consts.ClassType;
import com.robohorse.robopojogenerator.generator.consts.Imports;
import com.robohorse.robopojogenerator.models.GenerationModel;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

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
    public String proceedClassBody(ClassItem classItem, GenerationModel generationModel) {

        proceedImports(classItem);

        StringBuilder       classBodyBuilder = new StringBuilder();
        Map<String, String> classFields      = classItem.getClassFields();

        for (String objectName : classFields.keySet()) {

            String type       = proceedType(classFields.get(objectName));
            String annotation = proceedAnnotation(classItem.getAnnotation());
            String fieldName  = proceedField(objectName);

            classBodyBuilder.append(
                    classTemplateProcessor
                            .createKotlinDataClassField(type, fieldName, annotation));
        }

        if (classBodyBuilder.length() == 0) {
            // Kotlin don't allow empty constructor
            classBodyBuilder.append(ClassTemplate.FIELD_KOTLIN_DOT_DEFAULT);
        }
        else {
            // Remove the last comma
            classBodyBuilder.deleteCharAt(classBodyBuilder.lastIndexOf(","));
        }

        return classBodyBuilder.toString();
    }


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

        return classTemplateProcessor.createClassItemWithoutSemicolon(packagePath,
                                                                      imports,
                                                                      classTemplate);
    }


    private String proceedAnnotation(String annotation) {

        return annotation.replaceAll("@", "@field:");
    }


    private String proceedType(String type) {

        final String  regex   = "<([^<>]*)>";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(type);

        String innerType;
        if (matcher.find()) {
            // Type is List

            innerType = generateHelper.upperCaseFirst(matcher.group(0));

            if (innerType.equals(ClassType.OBJECT.getBoxed())) {
                innerType = "Any";
            }
            else if (innerType.equals(ClassType.INTEGER.getBoxed())) {
                innerType = "Int";
            }

            type = type.replaceAll(regex, "<" + innerType + ">");
            type = type.replaceAll(">", "?>");
        }
        else {
            // Type is object or primitive

            type = generateHelper.upperCaseFirst(type);

            if (type.equals("Object")) {
                type = "Any";
            }
        }

        return type;
    }


    /**
     * Remove List import and semicolon
     *
     * @param classItem The class item which is use to getting import
     */
    private void proceedImports(ClassItem classItem) {

        Set<String> classImports = classItem.getClassImports();

        Set<String> classImportWithoutSemicolon = new HashSet<String>();

        for (String classImport : classImports) {

            if (classImport.equals(Imports.LIST)) {
                // Kotlin don't use List import
                continue;
            }

            classImport = classImport.substring(0, classImport.indexOf(";"));
            classImportWithoutSemicolon.add(classImport);
        }

        classImports.clear();
        classImports.addAll(classImportWithoutSemicolon);
    }


    @Override
    public String createClassTemplate(ClassItem classItem, String classBody) {

        return classTemplateProcessor.createClassBodyKotlinDataClass(classItem, classBody);
    }
}
