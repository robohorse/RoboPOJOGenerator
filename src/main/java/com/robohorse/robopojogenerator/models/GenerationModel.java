package com.robohorse.robopojogenerator.models;

import com.robohorse.robopojogenerator.generator.consts.annotations.AnnotationEnum;

/**
 * Created by vadim on 28.09.16.
 */
public class GenerationModel {
    private boolean rewriteClasses;
    private boolean useKotlin;
    private AnnotationEnum annotationEnum;
    private String rootClassName;
    private String content;
    private boolean useSetters;
    private boolean useGetters;
    private boolean useStrings;

    public boolean isRewriteClasses() {
        return rewriteClasses;
    }

    public AnnotationEnum getAnnotationEnum() {
        return annotationEnum;
    }

    public String getRootClassName() {
        return rootClassName;
    }

    public String getContent() {
        return content;
    }

    public boolean isUseSetters() {
        return useSetters;
    }

    public boolean isUseGetters() {
        return useGetters;
    }

    public boolean isUseKotlin() {
        return useKotlin;
    }

    public boolean isUseStrings() {
        return useStrings;
    }

    public static class Builder {
        private GenerationModel instance;

        public Builder() {
            instance = new GenerationModel();
        }

        public Builder setRewriteClasses(boolean rewriteClasses) {
            instance.rewriteClasses = rewriteClasses;
            return this;
        }

        public Builder useKotlin(boolean useKotlin) {
            instance.useKotlin = useKotlin;
            return this;
        }

        public Builder setAnnotationItem(AnnotationEnum annotationEnum) {
            instance.annotationEnum = annotationEnum;
            return this;
        }

        public Builder setRootClassName(String rootClassName) {
            instance.rootClassName = rootClassName;
            return this;
        }

        public Builder setSettersAvailable(boolean available) {
            instance.useSetters = available;
            return this;
        }

        public Builder setGettersAvailable(boolean available) {
            instance.useGetters = available;
            return this;
        }
        public Builder setToStringAvailable(boolean available) {
            instance.useStrings = available;
            return this;
        }
        public Builder setContent(String content) {
            instance.content = content;
            return this;
        }

        public GenerationModel build() {
            return instance;
        }
    }
}
