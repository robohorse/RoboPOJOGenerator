package com.robohorse.robopojogenerator.generator.utils;

import com.google.common.base.CaseFormat;
import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.errors.custom.JSONStructureException;
import com.robohorse.robopojogenerator.errors.custom.WrongClassNameException;
import com.robohorse.robopojogenerator.generator.common.ClassItem;
import com.robohorse.robopojogenerator.generator.consts.ArrayItemsTemplate;
import com.robohorse.robopojogenerator.generator.consts.ClassTemplate;
import com.robohorse.robopojogenerator.generator.consts.ClassType;
import com.robohorse.robopojogenerator.models.InnerArrayModel;
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

    public String getClassName(String name) {
        return upperCaseFirst(name);
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
        return lowerCaseFirst(name);
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

    //TODO think about this method
    public void updateClassModel(StringBuilder classBodyBuilder) {
        if (classBodyBuilder.length() == 0) {
            // Kotlin don't allow empty constructor
            classBodyBuilder.append(ClassTemplate.FIELD_KOTLIN_DOT_DEFAULT);
        } else {
            // Remove the last comma
            classBodyBuilder.deleteCharAt(classBodyBuilder.lastIndexOf(","));
        }
    }

    //TODO think about this method
    public String proceedField(String objectName) {
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

    public String updateKotlinType(String type) {
        if (type.contains("<")) {
            type = type.replace("<" + ClassType.OBJECT.getBoxed() + ">", "<Any>");
            type = type.replace("<" + ClassType.INTEGER.getBoxed() + ">", "<Int>");
            type = type.replace(">", "?>");
        } else if (type.equals("Object")) {
            return "Any";
        }
        else {
            type = upperCaseFirst(type);
        }
        return type;
    }

    public String resolveMajorType(InnerArrayModel innerArrayModel) {
        String majorType = innerArrayModel.getMajorType();
        for (int i = 0; i < innerArrayModel.getInnerCount(); i++) {
            majorType = String.format(ArrayItemsTemplate.LIST_OF_ITEM, majorType);
        }
        return majorType;
    }
}
