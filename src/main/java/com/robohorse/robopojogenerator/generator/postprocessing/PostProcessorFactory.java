package com.robohorse.robopojogenerator.generator.postprocessing;

import com.robohorse.robopojogenerator.generator.consts.annotations.AnnotationEnum;
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

    public BasePostProcessor createPostProcessor(GenerationModel generationModel) {
        if (generationModel.isUseKotlin()) {
            return Injector.getAppComponent().newKotlinDataClassPostProcessor();

        } else if (generationModel.getAnnotationEnum() == AnnotationEnum.AUTO_VALUE_GSON) {
            return Injector.getAppComponent().newAutoValueClassPostProcessor();

        } else {
            return Injector.getAppComponent().newCommonJavaPostProcessor();
        }
    }
}
