package com.robohorse.robopojogenerator.generator.utils;

import com.robohorse.robopojogenerator.generator.ClassItem;
import com.robohorse.robopojogenerator.generator.consts.ClassType;

import javax.inject.Inject;

/**
 * Created by vadim on 25.09.16.
 */
public class ClassGenerateHelper {
    @Inject
    public ClassGenerateHelper() {
    }

    public String getClassName(String name) {
        if (name.length() > 1) {
            name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        }
        return name;
    }

    public void generateSetter(String fieldName, String fieldType, ClassItem classItem) {
        final String setter = "\tpublic void set" + getClassName(fieldName)
                + "(" + fieldType + " " + fieldName + "){\n"
                + "\t\tthis." + fieldName + " = " + fieldName + "; \n\t}";
        classItem.addClassMethod(setter);
    }

    public void generateGetter(String fieldName, String fieldType, ClassItem classItem) {
        String prefix = "get";
        if (ClassType.BOOLEAN.getPrimitive().equalsIgnoreCase(fieldType)) {
            prefix = "is";
        }
        final String getter = "\tpublic " + fieldType + " " + prefix
                + getClassName(fieldName)
                + "(){\n"
                + "\t\treturn " + fieldName + "; \n\t}";
        classItem.addClassMethod(getter);
    }

    public void setAnnotations(ClassItem classItem, String classAnnotation,
                               String annotation, String[] imports) {
        classItem.setClassAnnotation(classAnnotation);
        classItem.setAnnotation(annotation);

        for (String value : imports) {
            classItem.addClassImport(value);
        }
    }
}
