package com.robohorse.robopojogenerator.generator.consts;

/**
 * This is the LanguageItem class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/10/23 23:50
 */
public enum LanguageItem {

    POJO("Java POJO"),
    KOTLIN_DTO("Kotlin DTO");

    private String text;

    LanguageItem(String text) {
        this.text = text;
    }


    public String getText() {

        return text;
    }
}
