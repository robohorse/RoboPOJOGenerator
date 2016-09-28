package com.robohorse.robopojogenerator.generator.utils;

import javax.inject.Inject;

/**
 * Created by vadim on 25.09.16.
 */
public class ClassGenerateHelper {
    @Inject
    public ClassGenerateHelper() {
    }

    public String getClassName(String name) {
        name = name.replaceAll("[^A-Za-z0-9]", "");
        if (name.length() > 1) {
            name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        }
        return name + "Dto";
    }
}
