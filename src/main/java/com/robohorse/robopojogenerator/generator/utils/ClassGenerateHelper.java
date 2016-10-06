package com.robohorse.robopojogenerator.generator.utils;

import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.errors.custom.JSONStructureException;
import com.robohorse.robopojogenerator.errors.custom.WrongClassNameException;
import com.robohorse.robopojogenerator.generator.ClassItem;
import com.robohorse.robopojogenerator.generator.consts.ArrayItemsTemplate;
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

    public void setAnnotations(ClassItem classItem, String classAnnotation,
                               String annotation, String[] imports) {
        classItem.setClassAnnotation(classAnnotation);
        classItem.setAnnotation(annotation);

        for (String value : imports) {
            classItem.addClassImport(value);
        }
    }

    public String resolveMajorType(InnerArrayModel innerArrayModel) {
        String majorType = innerArrayModel.getMajorType();
        for (int i = 0; i < innerArrayModel.getInnerCount(); i++) {
            majorType = String.format(ArrayItemsTemplate.LIST_OF_ITEM, majorType);
        }
        return majorType;
    }
}
