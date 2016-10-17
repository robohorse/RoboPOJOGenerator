package com.robohorse.robopojogenerator.generator.processors;

import com.robohorse.robopojogenerator.generator.ClassItem;
import com.robohorse.robopojogenerator.generator.consts.ClassTemplate;
import com.robohorse.robopojogenerator.generator.consts.ClassType;
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper;

import javax.inject.Inject;

/**
 * Created by vadim on 05.10.16.
 */
public class ClassTemplateProcessor {
    @Inject
    ClassGenerateHelper classGenerateHelper;

    @Inject
    public ClassTemplateProcessor() {
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
                classGenerateHelper.lowerCaseFirst(field));
    }

    public String createFiled(String type, String name, String annotation) {
        final String field = String.format(ClassTemplate.FIELD,
                type,
                classGenerateHelper.getClassField(name));
        if (null != annotation && !annotation.isEmpty()) {
            return String.format(ClassTemplate.FIELD_ANNOTATED,
                    String.format(annotation, name),
                    field);
        } else {
            return field;
        }
    }

    public String createClassBody(ClassItem classItem, String classBody) {
        final String classItemBody = String.format(ClassTemplate.CLASS_BODY,
                classItem.getClassName(),
                classBody);
        final String classAnnotation = classItem.getClassAnnotation();
        if (null != classAnnotation && !classAnnotation.isEmpty()) {
            return String.format(ClassTemplate.CLASS_BODY_ANNOTATED,
                    classAnnotation,
                    classItemBody);
        } else {
            return classItemBody;
        }
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
}
