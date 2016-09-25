package com.robohorse.robopojogenerator.generator;

/**
 * Created by vadim on 25.09.16.
 */
public class ClassGenerateHelper {

    public String getClassName(String name) {
        if (null != name) {
            if (name.length() > 1) {
                return Character.toUpperCase(name.charAt(0)) + name.substring(1);
            }
        }
        return name;
    }
}
