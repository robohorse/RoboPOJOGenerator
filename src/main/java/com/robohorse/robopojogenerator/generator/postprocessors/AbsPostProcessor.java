package com.robohorse.robopojogenerator.generator.postprocessors;

import com.robohorse.robopojogenerator.models.ClassItemModel;
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

    public String proceed(ClassItemModel classItemModel, GenerationModel generationModel) {
        applyAnnotations(generationModel.getAnnotationItem(), classItemModel);
        return proceedClass(classItemModel, generationModel);
    }

    protected abstract void applyAnnotations(AnnotationItem item, ClassItemModel classItemModel);

    public abstract String proceedClassBody(ClassItemModel classItemModel, GenerationModel generationModel);

    public abstract String createClassTemplate(ClassItemModel classItemModel, String classBody);

    private String proceedClass(ClassItemModel classItemModel, GenerationModel generationModel) {
        final String classBody = generateHelper.updateClassBody(
                proceedClassBody(classItemModel, generationModel));
        final String classTemplate = createClassTemplate(classItemModel, classBody);
        final StringBuilder importsBuilder = proceedClassImports(classItemModel);

        return createClassItemText(classItemModel.getPackagePath(),
                importsBuilder.toString(),
                classTemplate);
    }

    protected StringBuilder proceedClassImports(ClassItemModel classItemModel) {
        final Set<String> imports = classItemModel.getClassImports();
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
