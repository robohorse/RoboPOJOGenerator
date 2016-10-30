package com.robohorse.robopojogenerator.generator;

import com.robohorse.robopojogenerator.models.ClassItemModel;
import com.robohorse.robopojogenerator.generator.processors.ClassProcessor;
import com.robohorse.robopojogenerator.models.GenerationModel;
import org.json.JSONObject;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by vadim on 22.09.16.
 */
public class RoboPOJOGenerator {
    @Inject
    ClassProcessor processor;

    @Inject
    public RoboPOJOGenerator() {
    }

    public Set<ClassItemModel> generate(GenerationModel model) {
        final Set<ClassItemModel> classItemModelSet = new HashSet<ClassItemModel>();
        final JSONObject jsonObject = new JSONObject(model.getContent());
        processor.proceed(jsonObject, model.getRootClassName(), classItemModelSet);
        return classItemModelSet;
    }
}
