package com.robohorse.robopojogenerator.model;

/**
 * Created by vadim on 02.10.16.
 */
public class InnerArrayModel {
    private String majorType;
    private int innerCount;

    public String getMajorType() {
        return majorType;
    }

    public int getInnerCount() {
        return innerCount;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }

    public void increaseCount() {
        innerCount++;
    }
}
