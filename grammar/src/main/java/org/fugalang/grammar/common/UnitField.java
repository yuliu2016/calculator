package org.fugalang.grammar.common;

import static org.fugalang.grammar.common.FieldType.*;

public class UnitField {

    private final RuleName ruleName;
    private final String fieldName;
    private final FieldType fieldType;
    private final ResultSource resultSource;
    private final String delimiter;

    /**
     * Used for field name conflict resolution
     */
    private int fieldSuffix = -1;

    public UnitField(
            RuleName ruleName,
            String fieldName,
            FieldType fieldType,
            ResultSource resultSource,
            String delimiter) {
        this.ruleName = ruleName;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.resultSource = resultSource;
        this.delimiter = delimiter;
    }

    public RuleName getRuleName() {
        return ruleName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public ResultSource getResultSource() {
        return resultSource;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public int getFieldSuffix() {
        return fieldSuffix;
    }

    public void setFieldSuffix(int fieldSuffix) {
        this.fieldSuffix = fieldSuffix;
    }

    public boolean isRequired() {
        return fieldType == Required || fieldType == RequiredList;
    }

    public boolean isSingular() {
        return fieldType == Required || fieldType == Optional;
    }

    public boolean isPredicate() {
        return fieldType == RequireTrue || fieldType == RequireFalse;
    }
}
