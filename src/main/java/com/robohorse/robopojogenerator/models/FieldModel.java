package com.robohorse.robopojogenerator.models;

/**
 * Created by vadim on 30.10.16.
 */
public class FieldModel {
    private String classType;
    private String fieldName;
    private String fieldNameFormatted;
    private String annotation;

    public String getClassType() {
        return classType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldNameFormatted() {
        return fieldNameFormatted;
    }

    public String getAnnotation() {
        return annotation;
    }

    public static class Builder {
        private FieldModel instance;

        public Builder() {
            instance = new FieldModel();
        }

        public Builder setClassType(String classType) {
            instance.classType = classType;
            return this;
        }

        public Builder setFieldName(String fieldName) {
            instance.fieldName = fieldName;
            return this;
        }

        public Builder setFieldNameFormatted(String fieldNameFormatted) {
            instance.fieldNameFormatted = fieldNameFormatted;
            return this;
        }

        public Builder setAnnotation(String annotation) {
            instance.annotation = annotation;
            return this;
        }

        public FieldModel build() {
            return instance;
        }
    }
}
