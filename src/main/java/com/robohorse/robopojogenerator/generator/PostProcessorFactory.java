package com.robohorse.robopojogenerator.generator;

import com.robohorse.robopojogenerator.generator.consts.AnnotationItem;
import com.robohorse.robopojogenerator.generator.consts.LanguageItem;
import com.robohorse.robopojogenerator.generator.postprocessors.AbsPostProcessor;
import com.robohorse.robopojogenerator.injections.Injector;
import com.robohorse.robopojogenerator.models.GenerationModel;

import javax.inject.Inject;

/**
 * Created by vadim on 23.10.16.
 */
public class PostProcessorFactory {

    @Inject
    public PostProcessorFactory() {
    }

    public AbsPostProcessor createPostProcessor(GenerationModel generationModel) {
//TODO rework this
        // Not support AutoValue yet
        if (generationModel.getLanguageItem().equals(LanguageItem.KOTLIN_DTO)
                && !generationModel.getAnnotationItem().equals(AnnotationItem.AUTO_VALUE_GSON)) {
            return Injector.getAppComponent().newKotlinDataClassPostProcessor();
        }

        switch (generationModel.getAnnotationItem()) {
            case AUTO_VALUE_GSON: {
                return Injector.getAppComponent().newAutoValueClassPostProcessor();
            }
            default: {
                return Injector.getAppComponent().newClassPostProcessor();
            }
        }
    }
}
