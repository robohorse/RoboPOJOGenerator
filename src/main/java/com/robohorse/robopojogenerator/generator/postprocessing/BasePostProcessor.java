package com.robohorse.robopojogenerator.generator.postprocessing;

import com.robohorse.robopojogenerator.generator.common.ClassItem;
import com.robohorse.robopojogenerator.generator.consts.annotations.AnnotationEnum;
import com.robohorse.robopojogenerator.generator.consts.templates.ClassTemplate;
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper;
import com.robohorse.robopojogenerator.generator.utils.ClassTemplateHelper;
import com.robohorse.robopojogenerator.models.GenerationModel;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by vadim on 23.10.16.
 */
public abstract class BasePostProcessor {
    @Inject
    public ClassGenerateHelper generateHelper;
    @Inject
    public ClassTemplateHelper classTemplateHelper;

    public String proceed(ClassItem classItem, GenerationModel generationModel) {
        applyAnnotations(generationModel.getAnnotationEnum(), classItem);
        return proceedClass(classItem, generationModel);
    }

    protected abstract void applyAnnotations(AnnotationEnum item, ClassItem classItem);

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
        return classTemplateHelper.createClassItem(packagePath,
                imports,
                classTemplate);
    }
}
