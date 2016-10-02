package com.robohorse.robopojogenerator.generator.consts;

/**
 * Created by vadim on 02.10.16.
 */
public interface PojoAnnotations {
    String INFO_ANNOTATION = "@Generated(\"com.robohorse.robopojogenerator\")\n";

    interface GSON {
        String CLASS_ANNOTATION = INFO_ANNOTATION;
        String ANNOTATION = "@SerializedName(\"%s\")\n\t@Expose";
    }

    interface LOGAN_SQUARE {
        String CLASS_ANNOTATION = INFO_ANNOTATION + "@JsonObject\n";
        String ANNOTATION = "@SerializedName(\"%s\")\n\t@JsonField(name =\"%s\")";
    }

    interface JACKSON {
        String CLASS_ANNOTATION = INFO_ANNOTATION;
        String ANNOTATION = "@JsonProperty(\"%s\")";
    }
}
