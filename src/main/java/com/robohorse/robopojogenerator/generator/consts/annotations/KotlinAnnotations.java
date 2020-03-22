package com.robohorse.robopojogenerator.generator.consts.annotations;

/**
 * This is the KotlinAnnotations class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/10/24 21:07
 */
public interface KotlinAnnotations {
    String INFO_ANNOTATION = "";

    interface GSON {
        String CLASS_ANNOTATION = INFO_ANNOTATION;
        String ANNOTATION = "@field:SerializedName(\"%1$s\")";
    }

    interface LOGAN_SQUARE {
        String CLASS_ANNOTATION = INFO_ANNOTATION + "\n@JsonObject";
        String ANNOTATION = "@field:SerializedName(\"%1$s\")\n\t@field:JsonField(name = arrayOf(\"%1$s\"))";
    }

    interface JACKSON {
        String CLASS_ANNOTATION = INFO_ANNOTATION;
        String ANNOTATION = "@field:JsonProperty(\"%1$s\")";
    }

    interface FAST_JSON {
        String CLASS_ANNOTATION = INFO_ANNOTATION;
        String ANNOTATION = "@JSONField(name=\"%1$s\")";
    }

    interface MOSHI {
        String CLASS_ANNOTATION = INFO_ANNOTATION;
        String ANNOTATION = "@Json(name=\"%1$s\")";
    }
}
