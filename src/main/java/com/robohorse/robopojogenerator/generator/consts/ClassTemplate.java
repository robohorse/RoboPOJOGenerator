package com.robohorse.robopojogenerator.generator.consts;

/**
 * Created by vadim on 05.10.16.
 */
public interface ClassTemplate {
    String NEW_LINE = "\n";
    String TAB = "\t";

    String CLASS_BODY = "public class %1$s" +
            "{" + NEW_LINE +
            "%2$s" + NEW_LINE +
            "}";

    String CLASS_BODY_ANNOTATED = "%1$s" + NEW_LINE +
            "%2$s";

    String CLASS_ROOT_IMPORTS = "package %1$s;" + NEW_LINE + NEW_LINE +
            "%2$s" + NEW_LINE
            + "%3$s";

    String CLASS_ROOT = "package %1$s;" + NEW_LINE + NEW_LINE +
            "%2$s" + NEW_LINE;

    String FIELD = TAB + "private %1$s %2$s;" + NEW_LINE;

    String FIELD_ANNOTATED = TAB + "%1$s" + NEW_LINE + "%2$s";

    String SETTER = TAB + "public void set%1$s(%2$s %3$s){" + NEW_LINE +
            TAB + TAB + "this.%3$s = %3$s;" + NEW_LINE
            + TAB + "}" + NEW_LINE;

    String GETTER = TAB + "public %3$s get%1$s(){" + NEW_LINE +
            TAB + TAB + "return %2$s;" + NEW_LINE
            + TAB + "}" + NEW_LINE;

    String GETTER_BOOLEAN = TAB + "public boolean is%1$s(){" + NEW_LINE +
            TAB + TAB + "return %2$s;" + NEW_LINE
            + TAB + "}" + NEW_LINE;
}
