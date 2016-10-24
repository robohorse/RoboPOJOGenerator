package com.robohorse.robopojogenerator.generator;

import com.robohorse.robopojogenerator.generator.processors.AbsClassProcessor;
import com.robohorse.robopojogenerator.models.GenerationModel;

import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;


/**
 * Created by vadim on 22.09.16.
 */
public class RoboPOJOGenerator {
    @Inject
    ProcessorFactory factory;

    @Inject
    public RoboPOJOGenerator() {
    }

    public Set<ClassItem> generate(GenerationModel model) {
        final Set<ClassItem> classItemSet = new HashSet<ClassItem>();
        final JSONObject jsonObject = new JSONObject(model.getContent());
        final AbsClassProcessor processor = factory.createClassProcessor(model);
        processor.proceed(jsonObject, model.getRootClassName(), classItemSet);
        return classItemSet;
    }
}
