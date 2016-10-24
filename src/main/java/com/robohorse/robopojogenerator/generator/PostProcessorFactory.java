package com.robohorse.robopojogenerator.generator;

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

        switch (generationModel.getLanguageItem()) {
            case KOTLIN_DTO: {
                return Injector.getAppComponent().newKotlinDataClassPostProcessor();
            }
        }

        switch (generationModel.getAnnotationItem()) {
            case AUTO_VALUE_GSON: {
                // I disable AutoValue when select Kotlin
                // So if the above is Kotlin, it won't get here
                return Injector.getAppComponent().newAutoValueClassPostProcessor();
            }
            default: {
                return Injector.getAppComponent().newClassPostProcessor();
            }
        }
    }
}
