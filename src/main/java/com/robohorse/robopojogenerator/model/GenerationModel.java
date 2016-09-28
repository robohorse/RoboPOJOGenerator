package com.robohorse.robopojogenerator.model;

import com.robohorse.robopojogenerator.generator.AnnotationItem;

/**
 * Created by vadim on 28.09.16.
 */
public class GenerationModel {
    private boolean rewriteClasses;
    private AnnotationItem annotationItem;
    private String rootClassName;
    private String content;

    public boolean isRewriteClasses() {
        return rewriteClasses;
    }

    public AnnotationItem getAnnotationItem() {
        return annotationItem;
    }

    public String getRootClassName() {
        return rootClassName;
    }

    public String getContent() {
        return content;
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

        public Builder setAnnotationItem(AnnotationItem annotationItem) {
            instance.annotationItem = annotationItem;
            return this;
        }

        public Builder setRootClassName(String rootClassName) {
            instance.rootClassName = rootClassName;
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
