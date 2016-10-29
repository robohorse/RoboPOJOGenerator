package com.robohorse.robopojogenerator.generator.utils;

import com.google.common.base.CaseFormat;
import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.errors.custom.JSONStructureException;
import com.robohorse.robopojogenerator.errors.custom.WrongClassNameException;
import com.robohorse.robopojogenerator.generator.common.ClassItem;
import com.robohorse.robopojogenerator.generator.consts.ArrayItemsTemplate;
import com.robohorse.robopojogenerator.generator.consts.ClassTemplate;
import com.robohorse.robopojogenerator.generator.consts.ReservedWords;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by vadim on 25.09.16.
 */
public class ClassGenerateHelper {
    @Inject
    public ClassGenerateHelper() {
    }

    public void validateJsonContent(String content) throws RoboPluginException {
        try {
            new JSONObject(content);
        } catch (JSONException exception) {
            throw new JSONStructureException();
        }
    }

    public void validateClassName(String name) throws RoboPluginException {
        if (null != name && name.length() > 1) {
            final String pattern = "^[a-zA-Z0-9]*$";
            if (name.matches(pattern)) {
                return;
            }
        }
        throw new WrongClassNameException();
    }

    public String updateClassBody(String classBody) {
        if (null != classBody && classBody.length() > 0) {
            final int lastIndex = classBody.length() - 1;
            if (classBody.charAt(lastIndex) == '\n') {
                return classBody.substring(0, lastIndex);
            }
        }
        return classBody;
    }

    public String getClassName(String name) {
        return upperCaseFirst(proceedField(name));
    }

    public String getClassNameWithItemPostfix(String name) {
        return String.format(ArrayItemsTemplate.ITEM_NAME, upperCaseFirst(name));
    }

    public String upperCaseFirst(String name) {
        if (name.length() > 1) {
            name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        }
        return name;
    }

    public String getClassField(String name) {
        return lowerCaseFirst(proceedField(name));
    }

    public String lowerCaseFirst(String name) {
        if (name.length() > 1) {
            name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
        }
        return name;
    }

    public void setAnnotations(ClassItem classItem, String classAnnotation,
                               String annotation, String[] imports) {
        classItem.setClassAnnotation(classAnnotation);
        classItem.setAnnotation(annotation);

        for (String value : imports) {
            classItem.addClassImport(value);
        }
    }

    public void updateClassModel(StringBuilder classBodyBuilder) {
        if (classBodyBuilder.length() == 0) {
            classBodyBuilder.append(ClassTemplate.FIELD_KOTLIN_DOT_DEFAULT);
        } else {
            classBodyBuilder.deleteCharAt(classBodyBuilder.lastIndexOf(","));
        }
    }

    public String proceedField(String objectName) {
        objectName = objectName
                .replaceAll("[^A-Za-z0-9]", "_")
                .replaceAll("_{2,}", "_");
        objectName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, objectName);
        if (objectName.length() == 0 || Character.isDigit(objectName.charAt(0))
                || ReservedWords.WORDS_SET.contains(objectName)) {
            objectName = "json_member_" + objectName;
        }
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, objectName);
    }
}
