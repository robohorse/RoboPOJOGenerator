package com.robohorse.robopojogenerator.generator.postprocessors;

import com.robohorse.robopojogenerator.generator.common.ClassItem;
import com.robohorse.robopojogenerator.generator.consts.AnnotationItem;
import com.robohorse.robopojogenerator.generator.consts.ClassTemplate;
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper;
import com.robohorse.robopojogenerator.generator.utils.ClassTemplateHelper;
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
    ClassTemplateHelper classTemplateHelper;

    public String proceed(ClassItem classItem, GenerationModel generationModel) {
        applyAnnotations(generationModel.getAnnotationItem(), classItem);
        return proceedClass(classItem, generationModel);
    }

    protected abstract void applyAnnotations(AnnotationItem item, ClassItem classItem);

    public abstract String proceedClassBody(ClassItem classItem, GenerationModel generationModel);

    public abstract String createClassTemplate(ClassItem classItem, String classBody);

    private String proceedClass(ClassItem classItem, GenerationModel generationModel) {
        final String classBody = generateHelper.updateClassBody(
                proceedClassBody(classItem, generationModel));
        final String classTemplate = createClassTemplate(classItem, classBody);
        final StringBuilder importsBuilder = proceedClassImports(classItem);

        return createClassItemText(classItem.getPackagePath(),
                importsBuilder.toString(),
                classTemplate);
    }

    protected StringBuilder proceedClassImports(ClassItem classItem) {
        final Set<String> imports = classItem.getClassImports();
        final StringBuilder importsBuilder = new StringBuilder();
        for (String importItem : imports) {
            importsBuilder.append(importItem);
            importsBuilder.append(ClassTemplate.NEW_LINE);
        }
        return importsBuilder;
    }

    protected String createClassItemText(String packagePath, String imports, String classTemplate) {
        return classTemplateHelper.createClassItem(
                packagePath,
                imports,
                classTemplate);
    }
}
