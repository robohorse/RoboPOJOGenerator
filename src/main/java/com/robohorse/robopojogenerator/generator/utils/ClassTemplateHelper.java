package com.robohorse.robopojogenerator.generator.utils;

import com.robohorse.robopojogenerator.generator.common.ClassItem;
import com.robohorse.robopojogenerator.generator.consts.ClassEnum;
import com.robohorse.robopojogenerator.generator.consts.templates.ClassTemplate;
import com.robohorse.robopojogenerator.models.FieldModel;

import javax.inject.Inject;
import java.util.Set;

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
        return String.format(
                ClassTemplate.SETTER,
                classGenerateHelper.upperCaseFirst(field),
                type,
                classGenerateHelper.lowerCaseFirst(field)
        );
    }

    public String createGetter(String field, String type) {
        final boolean isBoolean = ClassEnum.BOOLEAN.getPrimitive().equalsIgnoreCase(type);
        return String.format(
                isBoolean ? ClassTemplate.GETTER_BOOLEAN : ClassTemplate.GETTER,
                classGenerateHelper.upperCaseFirst(field),
                classGenerateHelper.lowerCaseFirst(field),
                type
        );
    }

    public String createToString(ClassItem classItem) {
        final String toString = generateToStingItem(classItem);
        return String.format(
                ClassTemplate.TO_STRING,
                classItem.getClassName(),
                toString
        );
    }

    private String generateToStingItem(ClassItem classItem) {
        boolean isFirstField = true;
        final StringBuilder fieldToStringStatement = new StringBuilder();
        final Set<String> fields = classItem.getClassFields().keySet();
        for (String field : fields) {
            fieldToStringStatement.append(
                    String.format(
                            ClassTemplate.TO_STRING_LINE,
                            classGenerateHelper.lowerCaseFirst(field),
                            classGenerateHelper.formatClassField(field),
                            isFirstField ? "" : ","
                    )
            );
            if (isFirstField) {
                isFirstField = false;
            }
        }
        return fieldToStringStatement.toString();
    }

    public String createFiled(FieldModel model) {
        final String field = String.format(
                ClassTemplate.FIELD,
                model.getClassType(),
                model.getFieldNameFormatted()
        );
        return createAnnotatedField(model.getFieldName(), model.getAnnotation(), field);
    }

    public String createAutoValueField(FieldModel model) {
        final String field = String.format(
                ClassTemplate.FIELD_AUTO_VALUE,
                model.getClassType(),
                model.getFieldNameFormatted()
        );
        return createAnnotatedField(model.getFieldName(), model.getAnnotation(), field);
    }

    public String createKotlinDataClassField(FieldModel model) {
        final String field = String.format(
                ClassTemplate.FIELD_KOTLIN_DTO,
                model.getFieldNameFormatted(),
                model.getClassType()
        ).replace(">", "?>");
        return createAnnotatedField(model.getFieldName(), model.getAnnotation(), field);
    }

    public String createClassBody(ClassItem classItem, String classBody) {
        final String classItemBody = String.format(
                ClassTemplate.CLASS_BODY,
                classItem.getClassName(),
                classBody
        );
        return createClassBodyAnnotated(classItem, classItemBody);
    }

    public String createTypeAdapter(ClassItem classItem) {
        return String.format(ClassTemplate.TYPE_ADAPTER, classItem.getClassName());
    }

    public String createClassBodyAbstract(ClassItem classItem, String classBody) {
        final String classItemBody = String.format(
                ClassTemplate.CLASS_BODY_ABSTRACT,
                classItem.getClassName(),
                classBody
        );
        return createClassBodyAnnotated(classItem, classItemBody);
    }

    public String createClassBodyKotlinDataClass(ClassItem classItem, String classBody) {
        final String classItemBody = String.format(
                ClassTemplate.CLASS_BODY_KOTLIN_DTO,
                classItem.getClassName(),
                classBody
        );
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
            return String.format(
                    ClassTemplate.CLASS_BODY_ANNOTATED,
                    classAnnotation,
                    classItemBody
            );
        } else {
            return classItemBody;
        }
    }

    private String createAnnotatedField(String name, String annotation, String field) {
        if (null != annotation && !annotation.isEmpty()) {
            return String.format(
                    ClassTemplate.FIELD_ANNOTATED,
                    String.format(annotation, name),
                    field
            );
        } else {
            return field;
        }
    }
}
