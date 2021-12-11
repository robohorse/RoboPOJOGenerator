package com.robohorse.robopojogenerator.generator.consts.annotations

internal sealed class KotlinAnnotations(
    val classAnnotation: String = EMPTY_ANNOTATION,
    val annotation: String
) {
    object GSON : KotlinAnnotations(
        annotation = "@field:SerializedName(\"%1\$s\")"
    )

    object LOGAN_SQUARE : KotlinAnnotations(
        classAnnotation = "@JsonObject",
        annotation = "@field:JsonField(name = [\"%1\$s\"])"
    )

    object JACKSON : KotlinAnnotations(
        annotation = "@field:JsonProperty(\"%1\$s\")"
    )

    object FAST_JSON : KotlinAnnotations(
        annotation = "@JSONField(name=\"%1\$s\")"
    )

    object MOSHI : KotlinAnnotations(
        annotation = "@Json(name=\"%1\$s\")"
    )
}

private const val EMPTY_ANNOTATION = ""
