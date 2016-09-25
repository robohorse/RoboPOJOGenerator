package com.robohorse.robopojogenerator.generator;

import com.robohorse.robopojogenerator.generator.processors.ClassProcessor;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by vadim on 22.09.16.
 */
public class RoboPOJOGenerator {
    private ClassProcessor classProcessor;

    public RoboPOJOGenerator() {
        classProcessor = new ClassProcessor();
    }

    public Set<ClassItem> generate(String string, String rootClassName) {
        Set<ClassItem> classItemSet = new HashSet<ClassItem>();
        JSONObject jsonObject = new JSONObject(string);
        classProcessor.proceed(jsonObject, rootClassName, classItemSet);
        return classItemSet;
    }
}
