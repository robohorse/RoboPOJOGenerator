package com.robohorse.robopojogenerator.debug;

import com.robohorse.robopojogenerator.generator.ClassItem;
import com.robohorse.robopojogenerator.generator.RoboPOJOGenerator;

import java.util.Set;

/**
 * Created by vadim on 25.09.16.
 */
public class Main {

    public static void main(String... args) {
        RoboPOJOGenerator roboPOJOGenerator = new RoboPOJOGenerator();
        Set<ClassItem> classItemSet = roboPOJOGenerator.generate(JSON, "Root");
        for (ClassItem classItem : classItemSet) {
            classItem.setPackagePath("com.robohorse.pojogenerator");
            classItem.setAnnotation("@JSON");
            //classPostProcessor.proceed(classItem);
            System.out.println(classItem.toString());
        }
    }

    private static final String JSON = "{\"data\":[]}";

}

