package com.robohorse.robopojogenerator.generator.utils;

import com.robohorse.robopojogenerator.generator.common.ClassItem;
import com.robohorse.robopojogenerator.generator.consts.ClassTemplate;
import com.robohorse.robopojogenerator.generator.consts.ClassType;

import javax.inject.Inject;

/**
 * Created by vadim on 05.10.16.
 */
public class ClassTemplateHelper {
    @Inject
    ClassGenerateHelper classGenerateHelper;

    @Inject
    public ClassTemplateHelper() {
    }

    public String createSetter(String field, String type) {
        field = classGenerateHelper.getClassField(field);
        return String.format(ClassTemplate.SETTER,
                classGenerateHelper.upperCaseFirst(field),
                type,
                classGenerateHelper.lowerCaseFirst(field));
    }

    public String createGetter(String field, String type) {
        field = classGenerateHelper.getClassField(field);
        final boolean isBoolean = ClassType.BOOLEAN.getPrimitive().equalsIgnoreCase(type);
        return String.format(isBoolean ? ClassTemplate.GETTER_BOOLEAN : ClassTemplate.GETTER,
                classGenerateHelper.upperCaseFirst(field),
                classGenerateHelper.lowerCaseFirst(field),
                type);
    }

    public String createFiled(String type, String name, String annotation) {
        final String field = String.format(ClassTemplate.FIELD,
                type,
                classGenerateHelper.getClassField(name));
        return createAnnotatedField(name, annotation, field);
    }

    public String createAutoValueField(String type, String name, String annotation) {
        final String field = String.format(ClassTemplate.FIELD_AUTO_VALUE,
                type,
                classGenerateHelper.getClassField(name));
        return createAnnotatedField(name, annotation, field);
    }

    public String createKotlinDataClassField(String type, String fieldName, String annotation) {
        final String field = String.format(ClassTemplate.FIELD_KOTLIN_DTO,
                classGenerateHelper.getClassField(fieldName),
                type)
                .replace(">", "?>");
        return createAnnotatedField(fieldName, annotation, field);
    }

    public String createClassBody(ClassItem classItem, String classBody) {
        final String classItemBody = String.format(ClassTemplate.CLASS_BODY,
                classItem.getClassName(),
                classBody);
        return createClassBodyAnnotated(classItem, classItemBody);
    }

    public String createTypeAdapter(ClassItem classItem) {
        return String.format(ClassTemplate.TYPE_ADAPTER, classItem.getClassName());
    }

    public String createClassBodyAbstract(ClassItem classItem, String classBody) {
        final String classItemBody = String.format(ClassTemplate.CLASS_BODY_ABSTRACT,
                classItem.getClassName(),
                classBody);
        return createClassBodyAnnotated(classItem, classItemBody);
    }

    public String createClassBodyKotlinDataClass(ClassItem classItem, String classBody) {
        final String classItemBody = String.format(ClassTemplate.CLASS_BODY_KOTLIN_DTO,
                classItem.getClassName(),
                classBody);

        return createClassBodyAnnotated(classItem, classItemBody);
    }

    public String createClassItem(String packagePath, String imports, String body) {
        if (null != imports && !imports.isEmpty()) {
            return String.format(ClassTemplate.CLASS_ROOT_IMPORTS,
                    packagePath,
                    imports,
                    body);
        } else {
            return String.format(ClassTemplate.CLASS_ROOT,
                    packagePath,
                    body);
        }
    }

    public String createClassItemWithoutSemicolon(String packagePath, String imports, String body) {
        if (null != imports && !imports.isEmpty()) {
            return String.format(ClassTemplate.CLASS_ROOT_IMPORTS_WITHOUT_SEMICOLON,
                    packagePath,
                    imports,
                    body);
        } else {
            return String.format(ClassTemplate.CLASS_ROOT_WITHOUT_SEMICOLON,
                    packagePath,
                    body);
        }
    }

    private String createClassBodyAnnotated(ClassItem classItem, String classItemBody) {
        final String classAnnotation = classItem.getClassAnnotation();
        if (null != classAnnotation && !classAnnotation.isEmpty()) {
            return String.format(ClassTemplate.CLASS_BODY_ANNOTATED,
                    classAnnotation,
                    classItemBody);
        } else {
            return classItemBody;
        }
    }

    private String createAnnotatedField(String name, String annotation, String field) {
        if (null != annotation && !annotation.isEmpty()) {
            return String.format(ClassTemplate.FIELD_ANNOTATED,
                    String.format(annotation, name),
                    field);
        } else {
            return field;
        }
    }
}
