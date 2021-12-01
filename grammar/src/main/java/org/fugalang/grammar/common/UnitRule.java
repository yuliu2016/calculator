package org.fugalang.grammar.common;

import org.fugalang.core.parser.RuleType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnitRule {
    private final int ruleIndex;
    private final RuleName ruleName;
    private final boolean leftRecursive;

    private RuleType ruleType;
    private final String grammarString;

    private final List<UnitField> fields = new ArrayList<>();
    private final Map<String, Integer> fieldNameCounter = new HashMap<>();

    public UnitRule(
            int ruleIndex,
            RuleName ruleName,
            boolean leftRecursive,
            String grammarString
    ) {
        this.ruleIndex = ruleIndex;
        this.ruleName = ruleName;
        this.leftRecursive = leftRecursive;
        this.grammarString = grammarString;
    }

    public int ruleIndex() {
        return ruleIndex;
    }

    public List<UnitField> fields() {
        return fields;
    }

    public boolean leftRecursive() {
        return leftRecursive;
    }

    public String grammarString() {
        return grammarString;
    }

    public RuleType ruleType() {
        return ruleType;
    }

    public void setRuleType(RuleType ruleType) {
        this.ruleType = ruleType;
    }

    public void guardMatchEmptyString() {
        if (fields.stream().noneMatch(UnitField::isRequired)) {
            throw new IllegalStateException("The rule" + ruleName +
                    " may match an empty string");
        }
    }

    public RuleName ruleName() {
        return ruleName;
    }

    public void addField(UnitField field) {
        var fieldName = field.getFieldName();

        // field name conflict resolution
        // eg. rule: subrule subrule would be made into
        // subrule and subrule1, respectively
        if (fieldNameCounter.containsKey(fieldName)) {
            int cnt = fieldNameCounter.get(fieldName) + 1;
            fieldNameCounter.put(fieldName, cnt);

            // modify the field name with the field count
            field.setFieldSuffix(cnt);
        } else {
            fieldNameCounter.put(fieldName, 0);
        }

        fields.add(field);
    }
}
