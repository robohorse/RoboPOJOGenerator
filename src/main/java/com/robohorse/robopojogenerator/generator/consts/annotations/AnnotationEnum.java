package com.robohorse.robopojogenerator.generator.consts.annotations;

/**
 * Created by vadim on 25.09.16.
 */
public enum AnnotationEnum {
    NONE("none"),
    GSON("GSON"),
    JACKSON("Jackson"),
    LOGAN_SQUARE("Logan Square"),
    FAST_JSON("FastJSON"),
    MOSHI("Moshi"),
    AUTO_VALUE_GSON("AutoValue (GSON)");

    private String text;

    AnnotationEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
