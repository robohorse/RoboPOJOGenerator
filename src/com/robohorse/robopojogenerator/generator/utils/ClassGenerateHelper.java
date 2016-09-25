package com.robohorse.robopojogenerator.generator.utils;

import java.io.File;

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

    public String resolvePackage(String path) {
        String[] items = path.split("src");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < items.length; i++) {
            stringBuilder.append(items[i]);
        }
        String result = stringBuilder.toString();
        result = result.replace(File.separator, ".");
        if (result.charAt(0) == '.') {
            return result.substring(1);
        } else {
            return result;
        }
    }
}
