package com.robohorse.robopojogenerator.generator.common;

import com.robohorse.robopojogenerator.generator.consts.templates.ArrayItemsTemplate;
import com.robohorse.robopojogenerator.generator.consts.ClassEnum;

/**
 * Created by vadim on 29.10.16.
 */
public class ClassField {
    private ClassEnum classEnum;
    private String className;
    private ClassField classField;

    public ClassField() {
    }

    public ClassField(ClassEnum classEnum) {
        this.classEnum = classEnum;
    }

    public ClassField(String className) {
        this.className = className;
    }

    public void setClassField(ClassField decorator) {
        if (classField == null) {
            classField = decorator;

        } else {
            classField.setClassField(decorator);
        }
    }

    public String getJavaItem() {
        return null != classField ? wrapListJava() :
                (null != className ? className : classEnum.getPrimitive());
    }

    public String getKotlinItem() {
        return null != classField ? wrapListKotlin() :
                (null != className ? className : classEnum.getKotlin());
    }

    private String wrapListJava() {
        return String.format(ArrayItemsTemplate.LIST_OF_ITEM, classField.getJavaBoxed());
    }

    private String wrapListKotlin() {
        return String.format(ArrayItemsTemplate.LIST_OF_ITEM, classField.getKotlinItem());
    }

    private String getJavaBoxed() {
        return null != classField ? wrapListJava() :
                (null != className ? className : classEnum.getBoxed());
    }
}
