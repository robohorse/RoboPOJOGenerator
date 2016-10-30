package com.robohorse.robopojogenerator.generator.common;

import com.robohorse.robopojogenerator.generator.consts.templates.ArrayItemsTemplate;
import com.robohorse.robopojogenerator.generator.consts.ClassEnum;

/**
 * Created by vadim on 29.10.16.
 */
public class ClassDecorator {
    private ClassEnum classEnum;
    private String className;
    private ClassDecorator classDecorator;

    public ClassDecorator() {
    }

    public ClassDecorator(ClassEnum classEnum) {
        this.classEnum = classEnum;
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
                (null != className ? className : classEnum.getPrimitive());
    }

    public String getKotlinItem() {
        return null != classDecorator ? wrapListKotlin() :
                (null != className ? className : classEnum.getKotlin());
    }

    private String wrapListJava() {
        return String.format(ArrayItemsTemplate.LIST_OF_ITEM, classDecorator.getJavaBoxed());
    }

    private String wrapListKotlin() {
        return String.format(ArrayItemsTemplate.LIST_OF_ITEM, classDecorator.getKotlinItem());
    }

    private String getJavaBoxed() {
        return null != classDecorator ? wrapListJava() :
                (null != className ? className : classEnum.getBoxed());
    }
}
