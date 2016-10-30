package com.robohorse.robopojogenerator.generator.utils;

import com.robohorse.robopojogenerator.generator.consts.ClassTemplate;
import com.robohorse.robopojogenerator.generator.consts.ClassType;
import com.robohorse.robopojogenerator.models.ClassItemModel;
import com.robohorse.robopojogenerator.models.FieldModel;

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
        return String.format(ClassTemplate.SETTER,
                classGenerateHelper.upperCaseFirst(field),
                type,
                classGenerateHelper.lowerCaseFirst(field));
    }

    public String createGetter(String field, String type) {
        final boolean isBoolean = ClassType.BOOLEAN.getPrimitive().equalsIgnoreCase(type);
        return String.format(isBoolean ? ClassTemplate.GETTER_BOOLEAN : ClassTemplate.GETTER,
                classGenerateHelper.upperCaseFirst(field),
                classGenerateHelper.lowerCaseFirst(field),
                type);
    }

    public String createFiled(String type, String name, String formatted, String annotation) {
        final String field = String.format(ClassTemplate.FIELD,
                type,
                formatted);
        return createAnnotatedField(name, annotation, field);
    }

    public String createAutoValueField(FieldModel model) {
        final String field = String.format(ClassTemplate.FIELD_AUTO_VALUE,
                model.getClassType(),
                model.getFieldNameFormatted());
        return createAnnotatedField(model.getFieldName(), model.getAnnotation(), field);
    }

    public String createKotlinDataClassField(FieldModel model) {
        final String field = String.format(ClassTemplate.FIELD_KOTLIN_DTO,
                model.getFieldNameFormatted(),
                model.getClassType())
                .replace(">", "?>");
        return createAnnotatedField(model.getFieldName(), model.getAnnotation(), field);
    }

    public String createClassBody(ClassItemModel classItemModel, String classBody) {
        final String classItemBody = String.format(ClassTemplate.CLASS_BODY,
                classItemModel.getClassName(),
                classBody);
        return createClassBodyAnnotated(classItemModel, classItemBody);
    }

    public String createTypeAdapter(ClassItemModel classItemModel) {
        return String.format(ClassTemplate.TYPE_ADAPTER, classItemModel.getClassName());
    }

    public String createClassBodyAbstract(ClassItemModel classItemModel, String classBody) {
        final String classItemBody = String.format(ClassTemplate.CLASS_BODY_ABSTRACT,
                classItemModel.getClassName(),
                classBody);
        return createClassBodyAnnotated(classItemModel, classItemBody);
    }

    public String createClassBodyKotlinDataClass(ClassItemModel classItemModel, String classBody) {
        final String classItemBody = String.format(ClassTemplate.CLASS_BODY_KOTLIN_DTO,
                classItemModel.getClassName(),
                classBody);

        return createClassBodyAnnotated(classItemModel, classItemBody);
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

    private String createClassBodyAnnotated(ClassItemModel classItemModel, String classItemBody) {
        final String classAnnotation = classItemModel.getClassAnnotation();
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
