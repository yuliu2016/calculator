package org.fugalang.core.grammar.gen;

import java.util.Objects;

public class ConvertedValue {
    private final String className;
    private final String fieldName;

    public ConvertedValue(String className, String fieldName) {
        this.className = className;
        this.fieldName = fieldName;
    }

    public String getClassName() {
        return className;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String toString() {
        return "ConvertedValue{" +
                "className='" + className + '\'' +
                ", fieldName='" + fieldName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConvertedValue that = (ConvertedValue) o;
        return Objects.equals(className, that.className) &&
                Objects.equals(fieldName, that.fieldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, fieldName);
    }
}
