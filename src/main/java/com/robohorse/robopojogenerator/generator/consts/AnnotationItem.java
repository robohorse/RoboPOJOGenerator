package com.robohorse.robopojogenerator.generator.consts;

/**
 * Created by vadim on 25.09.16.
 */
public enum AnnotationItem {
    NONE("none"),
    GSON("GSON"),
    JACKSON("Jackson"),
    LOGAN_SQUARE("Logan Square"),
    AUTO_VALUE_GSON("AutoValue (GSON)");
    
    private String text;

    AnnotationItem(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
