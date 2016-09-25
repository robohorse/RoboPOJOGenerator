package com.robohorse.robopojogenerator.generator.processors;

import com.robohorse.robopojogenerator.generator.AnnotationItem;
import com.robohorse.robopojogenerator.generator.ClassItem;
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper;

import java.util.Map;

/**
 * Created by vadim on 25.09.16.
 */
public class ClassPostProcessor {
    private ClassGenerateHelper generateHelper = new ClassGenerateHelper();

    public void proceed(ClassItem classItem, AnnotationItem annotationItem) {
        generateSettersAndGetters(classItem);
        generateAnnotation(annotationItem, classItem);
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

    private void generateAnnotation(AnnotationItem item, ClassItem classItem) {
        switch (item) {
            case GSON: {
                classItem.setClassAnnotation("@Generated(\"com.robohorse.robopojogenerator\")\n");
                classItem.setAnnotation("@SerializedName(\"%s\")\n\t@Expose");

                classItem.addClassImport("import javax.annotation.Generated;");
                classItem.addClassImport("import com.google.gson.annotations.Expose;");
                classItem.addClassImport("import com.google.gson.annotations.SerializedName;");
                break;
            }
            case LOGAN_SQUARE: {
                classItem.setClassAnnotation("@Generated(\"com.robohorse.robopojogenerator\")\n@JsonObject");
                classItem.setAnnotation("@SerializedName(\"%s\")\n\t@JsonField(name =\"%s\")");

                classItem.addClassImport("import javax.annotation.Generated;");
                classItem.addClassImport("import com.google.gson.annotations.SerializedName;");
                classItem.addClassImport("import com.bluelinelabs.logansquare.annotation.JsonObject;");
                classItem.addClassImport("import com.bluelinelabs.logansquare.annotation.JsonField;");
            }
        }
    }
}
