package com.robohorse.robopojogenerator.generator;

import com.robohorse.robopojogenerator.generator.common.ClassItem;
import com.robohorse.robopojogenerator.generator.processing.ClassProcessor;
import com.robohorse.robopojogenerator.models.GenerationModel;
import org.json.JSONObject;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

    public Set<ClassItem> generate(GenerationModel model) {
        final JSONObject jsonObject = new JSONObject(model.getContent());
        final Map<String, ClassItem> map = new HashMap<String, ClassItem>();
        processor.proceed(jsonObject, model.getRootClassName(), map);
        return new HashSet<ClassItem>(map.values());
    }
}
