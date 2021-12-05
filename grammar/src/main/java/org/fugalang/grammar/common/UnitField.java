package org.fugalang.grammar.common;

import static org.fugalang.grammar.common.FieldType.*;

public record UnitField(
        RuleName ruleName,
        FieldName fieldName,
        FieldType fieldType,
        ResultSource resultSource,
        TokenEntry delimiter,
        ResultClause resultClause
) {

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
