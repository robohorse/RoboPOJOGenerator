package com.robohorse.robopojogenerator.generator;

import com.robohorse.robopojogenerator.generator.postprocessors.ClassPostProcessor;
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

    public ClassPostProcessor createPostProcessor(GenerationModel generationModel) {
        switch (generationModel.getAnnotationItem()) {
            default: {
                return Injector.getAppComponent().newClassPostProcessor();
            }
        }
    }
}
