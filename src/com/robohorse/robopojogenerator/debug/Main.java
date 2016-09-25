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

    private static final String JSON = "{\"widget\": {\n" +
            "    \"debug\": true,\n" +
            "    \"window\": {\n" +
            "        \"title\": \"Sample Konfabulator Widget\",\n" +
            "        \"name\": \"main_window\",\n" +
            "        \"width\": 500,\n" +
            "        \"height\": 500\n" +
            "    },\n" +
            "    \"image\": { \n" +
            "        \"src\": \"Images/Sun.png\",\n" +
            "        \"name\": \"sun1\",\n" +
            "        \"hOffset\": [1,2,3],\n" +
            "        \"vOffset\": 250,\n" +
            "        \"alignment\": \"center\"\n" +
            "    },\n" +
            "    \"text\": {\n" +
            "        \"data\": \"Click Here\",\n" +
            "        \"size\": 36,\n" +
            "        \"style\": \"bold\",\n" +
            "        \"name\": \"text1\",\n" +
            "        \"hOffset\": 250,\n" +
            "        \"vOffset\": 100,\n" +
            "        \"alignment\": \"center\",\n" +
            "        \"onMouseUp\": \"sun1.opacity = (sun1.opacity / 100) * 90;\"\n" +
            "    }\n" +
            "}}";

}

