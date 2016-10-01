package com.robohorse.robopojogenerator.generator;

/**
 * Created by vadim on 25.09.16.
 */
public enum AnnotationItem {
    NONE("none"),
    GSON("GSON"),
    JACKSON("Jackson"),
    LOGAN_SQUARE("Logan Square");
    
    private String text;

    AnnotationItem(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
