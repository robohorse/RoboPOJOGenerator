package com.robohorse.robopojogenerator.generator.postprocessors;

import com.robohorse.robopojogenerator.generator.ClassItem;
import com.robohorse.robopojogenerator.generator.consts.AnnotationItem;
import com.robohorse.robopojogenerator.generator.consts.ClassTemplate;
import com.robohorse.robopojogenerator.generator.consts.Imports;
import com.robohorse.robopojogenerator.generator.consts.PojoAnnotations;
import com.robohorse.robopojogenerator.generator.processors.ClassTemplateProcessor;
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper;
import com.robohorse.robopojogenerator.models.GenerationModel;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by vadim on 23.10.16.
 */
public abstract class AbsPostProcessor {
    @Inject
    ClassGenerateHelper generateHelper;
    @Inject
    ClassTemplateProcessor classTemplateProcessor;

    public String proceed(ClassItem classItem, GenerationModel generationModel) {
        applyAnnotations(generationModel.getAnnotationItem(), classItem);
        return proceedClass(classItem, generationModel);
    }

    public abstract String proceedClassBody(ClassItem classItem, GenerationModel generationModel);

    private String proceedClass(ClassItem classItem, GenerationModel generationModel) {
        final String classBody = proceedClassBody(classItem, generationModel);
        final String classTemplate = classTemplateProcessor.createClassBody(classItem, classBody);
        final Set<String> imports = classItem.getClassImports();
        final StringBuilder importsBuilder = new StringBuilder();
        for (String importItem : imports) {
            importsBuilder.append(importItem);
            importsBuilder.append(ClassTemplate.NEW_LINE);
        }
        return classTemplateProcessor.createClassItem(
                classItem.getPackagePath(),
                importsBuilder.toString(),
                classTemplate);
    }

    private void applyAnnotations(AnnotationItem item, ClassItem classItem) {
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
            case AUTO_VALUE_GSON: {
                generateHelper.setAnnotations(classItem,
                        PojoAnnotations.AUTO_VALUE_GSON.CLASS_ANNOTATION,
                        PojoAnnotations.AUTO_VALUE_GSON.ANNOTATION,
                        Imports.AUTO_VALUE_GSON.IMPORTS);
                break;
            }
        }
    }
}
