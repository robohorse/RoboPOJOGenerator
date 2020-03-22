package com.robohorse.robopojogenerator.generator.consts.annotations;

/**
 * Created by vadim on 02.10.16.
 */
public interface PojoAnnotations {
    String INFO_ANNOTATION = "";

    interface GSON {
        String CLASS_ANNOTATION = INFO_ANNOTATION;
        String ANNOTATION = "@SerializedName(\"%1$s\")";
    }

    interface LOGAN_SQUARE {
        String CLASS_ANNOTATION = INFO_ANNOTATION + "\n@JsonObject";
        String ANNOTATION = "@SerializedName(\"%1$s\")\n\t@JsonField(name =\"%1$s\")";
    }

    interface JACKSON {
        String CLASS_ANNOTATION = INFO_ANNOTATION;
        String ANNOTATION = "@JsonProperty(\"%1$s\")";
    }

    interface AUTO_VALUE_GSON {
        String CLASS_ANNOTATION = INFO_ANNOTATION + "\n@AutoValue";
        String ANNOTATION = "@SerializedName(\"%1$s\")";
    }

    interface FAST_JSON {
        String CLASS_ANNOTATION = INFO_ANNOTATION;
        String ANNOTATION = "@JSONField(name=\"%1$s\")";
    }

    interface MOSHI {
        String CLASS_ANNOTATION = INFO_ANNOTATION;
        String ANNOTATION = "@Json(name = \"%1$s\")";
    }
}
