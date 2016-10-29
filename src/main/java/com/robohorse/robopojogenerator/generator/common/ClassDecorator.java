package com.robohorse.robopojogenerator.generator.common;

import com.robohorse.robopojogenerator.generator.consts.ArrayItemsTemplate;
import com.robohorse.robopojogenerator.generator.consts.ClassType;

/**
 * Created by vadim on 29.10.16.
 */
public class ClassDecorator {
    private ClassType classType;
    private String className;
    private ClassDecorator classDecorator;

    public ClassDecorator() {
    }

    public ClassDecorator(ClassType classType) {
        this.classType = classType;
    }

    public ClassDecorator(String className) {
        this.className = className;
    }

    public void setClassDecorator(ClassDecorator decorator) {
        if (classDecorator == null) {
            classDecorator = decorator;

        } else {
            classDecorator.setClassDecorator(decorator);
        }
    }

    public String getJavaItem() {
        return null != classDecorator ? wrapListJava() :
                (null != className ? className : classType.getPrimitive());
    }

    public String getKotlinItem() {
        return null != classDecorator ? wrapListKotlin() :
                (null != className ? className : classType.getKotlin());
    }

    private String wrapListJava() {
        return String.format(ArrayItemsTemplate.LIST_OF_ITEM, classDecorator.getJavaBoxed());
    }

    private String wrapListKotlin() {
        return String.format(ArrayItemsTemplate.LIST_OF_ITEM, classDecorator.getKotlinItem());
    }

    private String getJavaBoxed() {
        return null != classDecorator ? wrapListJava() :
                (null != className ? className : classType.getBoxed());
    }
}
