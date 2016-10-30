package com.robohorse.robopojogenerator.generator.postprocessing.common;

import com.robohorse.robopojogenerator.generator.postprocessing.BasePostProcessor;
import com.robohorse.robopojogenerator.generator.common.ClassItem;
import com.robohorse.robopojogenerator.generator.consts.annotations.AnnotationEnum;
import com.robohorse.robopojogenerator.generator.consts.templates.ImportsTemplate;
import com.robohorse.robopojogenerator.generator.consts.annotations.PojoAnnotations;

/**
 * Created by vadim on 24.10.16.
 */
public abstract class JavaPostProcessor extends BasePostProcessor {

    protected void applyAnnotations(AnnotationEnum item, ClassItem classItem) {
        switch (item) {
            case GSON: {
                generateHelper.setAnnotations(classItem,
                        PojoAnnotations.GSON.CLASS_ANNOTATION,
                        PojoAnnotations.GSON.ANNOTATION,
                        ImportsTemplate.GSON.IMPORTS);
                break;
            }
            case LOGAN_SQUARE: {
                generateHelper.setAnnotations(classItem,
                        PojoAnnotations.LOGAN_SQUARE.CLASS_ANNOTATION,
                        PojoAnnotations.LOGAN_SQUARE.ANNOTATION,
                        ImportsTemplate.LOGAN_SQUARE.IMPORTS);
                break;
            }
            case JACKSON: {
                generateHelper.setAnnotations(classItem,
                        PojoAnnotations.JACKSON.CLASS_ANNOTATION,
                        PojoAnnotations.JACKSON.ANNOTATION,
                        ImportsTemplate.JACKSON.IMPORTS);
                break;
            }
            case AUTO_VALUE_GSON: {
                generateHelper.setAnnotations(classItem,
                        PojoAnnotations.AUTO_VALUE_GSON.CLASS_ANNOTATION,
                        PojoAnnotations.AUTO_VALUE_GSON.ANNOTATION,
                        ImportsTemplate.AUTO_VALUE_GSON.IMPORTS);
                break;
            }
        }
    }
}