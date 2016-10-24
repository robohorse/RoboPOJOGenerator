package com.robohorse.robopojogenerator.generator;

import com.robohorse.robopojogenerator.generator.processors.AbsClassProcessor;
import com.robohorse.robopojogenerator.injections.Injector;
import com.robohorse.robopojogenerator.models.GenerationModel;

import javax.inject.Inject;

/**
 * This is the ProcessorFactory class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/10/24 21:43
 */
class ProcessorFactory {

    @Inject
    public ProcessorFactory() {

    }

    AbsClassProcessor createClassProcessor(GenerationModel generationModel) {
        switch (generationModel.getLanguageItem()) {
            default: {
                return Injector.getAppComponent().newClassProcessor();
            }
        }
    }
}
