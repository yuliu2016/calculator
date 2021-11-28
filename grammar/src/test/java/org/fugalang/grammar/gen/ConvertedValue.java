package org.fugalang.grammar.gen;

import java.util.Objects;

@Deprecated
public class ConvertedValue {
    private final String className;
    private final String fieldName;
    private final String sourceLiteral;

    public ConvertedValue(String className, String fieldName, String sourceLiteral) {
        this.className = className;
        this.fieldName = fieldName;
        this.sourceLiteral = sourceLiteral;
    }

    public String getClassName() {
        return className;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getSourceLiteral() {
        return sourceLiteral;
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
