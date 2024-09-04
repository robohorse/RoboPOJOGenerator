package com.robohorse.robopojogenerator.properties.annotations

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

    object JAKATRA : KotlinAnnotations(
        annotation = "@JsonbProperty(\"%1\$s\")"
    )

    data class MOSHI(
        val adapterClassAnnotation: String = "@JsonClass(generateAdapter = true)"
    ) : KotlinAnnotations(
        annotation = "@Json(name=\"%1\$s\")"
    )
}

private const val EMPTY_ANNOTATION = ""
