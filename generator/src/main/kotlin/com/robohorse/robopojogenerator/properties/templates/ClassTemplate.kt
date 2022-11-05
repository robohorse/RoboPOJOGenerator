package com.robohorse.robopojogenerator.properties.templates

internal object ClassTemplate {
    const val KOTLIN_DATA_CLASS = "data"
    const val NEW_LINE = "\n"
    const val TAB = "\t"
    const val CLASS_BODY = "public class %1\$s" +
        "{" + NEW_LINE +
        "%2\$s" + NEW_LINE +
        "}"
    const val CLASS_BODY_ABSTRACT = "public abstract class %1\$s" +
        "{" + NEW_LINE +
        "%2\$s" + NEW_LINE +
        "}"

    const val CLASS_BODY_RECORDS = "public record %1\$s(\n%2\$s\n)" + " {\n}"

    const val CLASS_BODY_KOTLIN_DTO = "$KOTLIN_DATA_CLASS class %1\$s" +
        "(" + NEW_LINE +
        "%2\$s" + NEW_LINE +
        ")"
    const val CLASS_BODY_KOTLIN_DTO_PARCELABLE = "@Parcelize\n$KOTLIN_DATA_CLASS class %1\$s" +
        "(" + NEW_LINE +
        "%2\$s" + NEW_LINE +
        ") : Parcelable"
    const val CLASS_BODY_ANNOTATED = "%1\$s" + NEW_LINE +
        "%2\$s"
    const val CLASS_ROOT_IMPORTS = (
        "package %1\$s;" + NEW_LINE + NEW_LINE +
            "%2\$s" + NEW_LINE +
            "%3\$s"
        )
    const val CLASS_ROOT_IMPORTS_WITHOUT_SEMICOLON = (
        "package %1\$s" + NEW_LINE + NEW_LINE +
            "%2\$s" + NEW_LINE +
            "%3\$s"
        )
    const val CLASS_ROOT = "package %1\$s;" + NEW_LINE + NEW_LINE +
        "%2\$s" + NEW_LINE
    const val CLASS_ROOT_WITHOUT_SEMICOLON = "package %1\$s" + NEW_LINE + NEW_LINE +
        "%2\$s" + NEW_LINE
    const val CLASS_ROOT_NO_PACKAGE = "%1\$s" + NEW_LINE
    const val FIELD = "$TAB%1\$s %2\$s;$NEW_LINE"
    const val FIELD_WITH_VISIBILITY = "$TAB%1\$s %2\$s %3\$s;$NEW_LINE"
    const val FIELD_AUTO_VALUE = TAB + "public abstract %1\$s %2\$s();" + NEW_LINE
    const val FIELD_KOTLIN_DTO = TAB + "val %1\$s: %2\$s? = null" + "," + NEW_LINE
    const val FIELD_KOTLIN_DTO_NON_NULL = TAB + "val %1\$s: %2\$s" + "," + NEW_LINE
    const val FIELD_JAVA_RECORD = "$TAB%1\$s %2\$s,$NEW_LINE"
    const val FIELD_KOTLIN_DOT_DEFAULT = TAB + "val any: Any? = null"
    const val FIELD_ANNOTATED = "$NEW_LINE$TAB%1\$s$NEW_LINE%2\$s"
    const val TYPE_ADAPTER = (
        TAB + "public static TypeAdapter<%1\$s> typeAdapter(Gson gson) {" +
            NEW_LINE +
            TAB + TAB + "return new AutoValue_%1\$s.GsonTypeAdapter(gson);" + NEW_LINE +
            TAB + "}" + NEW_LINE
        )
    const val SETTER = (
        TAB + "public void set%1\$s(%2\$s %3\$s){" + NEW_LINE +
            TAB + TAB + "this.%3\$s = %3\$s;" + NEW_LINE +
            TAB + "}" + NEW_LINE
        )
    const val GETTER = (
        TAB + "public %3\$s get%1\$s(){" + NEW_LINE +
            TAB + TAB + "return %2\$s;" + NEW_LINE +
            TAB + "}" + NEW_LINE
        )
    const val GETTER_BOOLEAN = (
        TAB + "public %3\$s is%1\$s(){" + NEW_LINE +
            TAB + TAB + "return %2\$s;" + NEW_LINE +
            TAB + "}" + NEW_LINE
        )
    const val TO_STRING = (
        TAB + "@Override" + NEW_LINE + " " + TAB +
            "public String toString(){" + NEW_LINE +
            TAB + TAB + "return " + NEW_LINE +
            TAB + TAB + TAB + "\"%1\$s{\" + " + NEW_LINE +
            "%2\$s" +
            TAB + TAB + TAB + "\"}\";" + NEW_LINE +
            TAB + TAB + "}"
        )
    const val TO_STRING_LINE = "$TAB$TAB$TAB\"%3\$s%1\$s = \'\" + %2\$s + \'\\\'\' + $NEW_LINE"
}
