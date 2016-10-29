package com.robohorse.robopojogenerator.generator.consts;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vadim on 29.10.16.
 */
public interface ReservedWords {
    String[] WORDS = {
            "abstract",
            "continue",
            "for",
            "new",
            "switch",
            "assert",
            "default",
            "if",
            "package",
            "synchronized",
            "boolean",
            "do",
            "goto",
            "private",
            "this",
            "break",
            "double",
            "implements",
            "protected",
            "throw",
            "byte",
            "else",
            "import",
            "public",
            "throws",
            "case",
            "enum",
            "instanceof",
            "return",
            "transient",
            "catch",
            "extends",
            "int",
            "short",
            "try",
            "char",
            "final",
            "interface",
            "static",
            "void",
            "class",
            "finally",
            "long",
            "strictfp",
            "volatile",
            "const",
            "float",
            "native",
            "super",
            "while"
    };

    Set<String> WORDS_SET = new HashSet<String>(Arrays.asList(WORDS));
}
