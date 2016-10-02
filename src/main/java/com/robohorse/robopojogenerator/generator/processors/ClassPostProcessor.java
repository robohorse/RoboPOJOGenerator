package com.robohorse.robopojogenerator.generator.processors;

import com.robohorse.robopojogenerator.generator.ClassItem;
import com.robohorse.robopojogenerator.generator.consts.AnnotationItem;
import com.robohorse.robopojogenerator.generator.consts.Imports;
import com.robohorse.robopojogenerator.generator.consts.PojoAnnotations;
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by vadim on 25.09.16.
 */
public class ClassPostProcessor {
    @Inject
    ClassGenerateHelper generateHelper;

    @Inject
    public ClassPostProcessor() {
    }

    public void proceed(ClassItem classItem, AnnotationItem annotationItem) {
        generateSettersAndGetters(classItem);
        generateAnnotations(annotationItem, classItem);
    }

    private void generateSettersAndGetters(ClassItem classItem) {
        Map<String, String> classFields = classItem.getClassFields();
        for (String objectName : classFields.keySet()) {
            generateHelper.generateSetter(objectName, classFields.get(objectName), classItem);
            generateHelper.generateGetter(objectName, classFields.get(objectName), classItem);
        }
    }

    private void generateAnnotations(AnnotationItem item, ClassItem classItem) {
        switch (item) {
            case GSON: {
                generateHelper.setAnnotations(classItem,
                        PojoAnnotations.GSON.CLASS_ANNOTATION,
                        PojoAnnotations.GSON.ANNOTATION,
                        Imports.GSON.IMPORTS);
                break;
            }
            case LOGAN_SQUARE: {
                generateHelper.setAnnotations(classItem,
                        PojoAnnotations.LOGAN_SQUARE.CLASS_ANNOTATION,
                        PojoAnnotations.LOGAN_SQUARE.ANNOTATION,
                        Imports.LOGAN_SQUARE.IMPORTS);
                break;
            }
            case JACKSON: {
                generateHelper.setAnnotations(classItem,
                        PojoAnnotations.JACKSON.CLASS_ANNOTATION,
                        PojoAnnotations.JACKSON.ANNOTATION,
                        Imports.JACKSON.IMPORTS);
                break;
            }
        }
    }
}
