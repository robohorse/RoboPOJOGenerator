package com.robohorse.robopojogenerator.generator;

import java.util.Map;

/**
 * Created by vadim on 25.09.16.
 */
public class ClassPostProcessor {
    private ClassGenerateHelper generateHelper = new ClassGenerateHelper();

    public void proceed(ClassItem classItem) {
        generateSettersAndGetters(classItem);
    }

    private void generateSettersAndGetters(ClassItem classItem) {
        Map<String, String> classFields = classItem.getClassFields();
        for (String objectName : classFields.keySet()) {
            generateSetter(objectName, classFields.get(objectName), classItem);
            generateGetter(objectName, classFields.get(objectName), classItem);
        }
    }

    private void generateSetter(String fieldName, String fieldType, ClassItem classItem) {
        final String setter = "\tpublic void set" + generateHelper.getClassName(fieldName)
                + "(" + fieldType + " " + fieldName + "){\n"
                + "\t\tthis." + fieldName + " = " + fieldName + "; \n\t}";
        classItem.addClassMethod(setter);
    }

    private void generateGetter(String fieldName, String fieldType, ClassItem classItem) {
        String prefix = "get";
        if ("boolean".equalsIgnoreCase(fieldType)) {
            prefix = "is";
        }
        final String getter = "\tpublic " + fieldType + " " + prefix
                + generateHelper.getClassName(fieldName)
                + "(){\n"
                + "\t\treturn " + fieldName + "; \n\t}";
        classItem.addClassMethod(getter);
    }
}
