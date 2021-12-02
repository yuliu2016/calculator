package org.fugalang.grammar.common;

import static org.fugalang.grammar.common.FieldType.*;

public class UnitField {

    private final RuleName ruleName;
    private final String fieldName;
    private final FieldType fieldType;
    private final ResultSource resultSource;
    private final TokenEntry delimiter;

    /**
     * Used for field name conflict resolution
     */
    private int fieldSuffix = -1;

    public UnitField(
            RuleName ruleName,
            String fieldName,
            FieldType fieldType,
            ResultSource resultSource,
            TokenEntry delimiter) {
        this.ruleName = ruleName;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.resultSource = resultSource;
        this.delimiter = delimiter;
    }

    public RuleName ruleName() {
        return ruleName;
    }

    public String fieldName() {
        return fieldName;
    }

    public FieldType fieldType() {
        return fieldType;
    }

    public ResultSource resultSource() {
        return resultSource;
    }

    public TokenEntry delimiter() {
        return delimiter;
    }

    public String properFieldName() {
        if (fieldSuffix != -1) {
            return fieldName() + "_" + fieldSuffix;
        }
        return fieldName();
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
