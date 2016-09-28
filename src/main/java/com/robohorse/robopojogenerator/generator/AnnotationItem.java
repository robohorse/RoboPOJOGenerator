package com.robohorse.robopojogenerator.generator;

/**
 * Created by vadim on 25.09.16.
 */
public enum AnnotationItem {
    GSON("GSON"), LOGAN_SQUARE("Logan Square"), NONE("none");
    private String text;

    AnnotationItem(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
