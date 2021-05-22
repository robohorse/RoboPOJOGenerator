package com.robohorse.robopojogenerator.generator.consts.annotations

sealed class PojoAnnotations(
        val classAnnotation: String = EMPTY_ANNOTATION,
        val annotation: String = EMPTY_ANNOTATION
) {
    object GSON : PojoAnnotations(
            annotation = "@SerializedName(\"%1\$s\")"
    )

    object LOGAN_SQUARE : PojoAnnotations(
            classAnnotation = "@JsonObject",
            annotation = "@JsonField(name =\"%1\$s\")"
    )

    object JACKSON : PojoAnnotations(
            annotation = "@JsonProperty(\"%1\$s\")"
    )

    object AUTO_VALUE_GSON : PojoAnnotations(
            classAnnotation = "@AutoValue",
            annotation = "@SerializedName(\"%1\$s\")"
    )

    object FAST_JSON : PojoAnnotations(
            annotation = "@JSONField(name=\"%1\$s\")"
    )

    object MOSHI : PojoAnnotations(
            annotation = "@Json(name = \"%1\$s\")"
    )

    object LOMBOK : PojoAnnotations()
}

private const val EMPTY_ANNOTATION = ""
