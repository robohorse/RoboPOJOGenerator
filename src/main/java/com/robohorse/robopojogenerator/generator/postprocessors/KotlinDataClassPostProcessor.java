package com.robohorse.robopojogenerator.generator.postprocessors;

import com.robohorse.robopojogenerator.generator.ClassItem;
import com.robohorse.robopojogenerator.generator.consts.ClassType;
import com.robohorse.robopojogenerator.models.GenerationModel;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the KotlinDataClassPostProcessor class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/10/24 00:30
 */
public class KotlinDataClassPostProcessor extends AbsPostProcessor {

    @Override
    public String proceedClassBody(ClassItem classItem, GenerationModel generationModel) {

        removeClassImportSemicolon(classItem);

        StringBuilder       classBodyBuilder = new StringBuilder();
        Map<String, String> classFields      = classItem.getClassFields();

        for (String objectName : classFields.keySet()) {

            String type       = proceedType(classFields.get(objectName));
            String annotation = proceedAnnotation(classItem.getAnnotation());

            classBodyBuilder.append(
                    classTemplateProcessor
                            .createKotlinDataClassField(type, objectName, annotation));
        }

        return classBodyBuilder.toString();
    }


    private String proceedAnnotation(String annotation) {

        return annotation.replaceAll("@", "@field:");
    }


    private String proceedType(String type) {

        final String regex = "<([^<>]*)>";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(type);

        String innerType;
        if (matcher.groupCount() != 0) {
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


    private void removeClassImportSemicolon(ClassItem classItem) {

        Set<String> classImports = classItem.getClassImports();

        Set<String> classImportWithoutSemicolon = new HashSet<String>();

        for (String classImport : classImports) {
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
