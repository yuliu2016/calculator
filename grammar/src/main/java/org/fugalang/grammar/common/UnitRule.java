package org.fugalang.grammar.common;

import org.fugalang.core.parser.RuleType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnitRule {
    private final RuleName ruleName;
    private final boolean leftRecursive;

    private boolean containsList;
    private boolean containsTokenType;
    private RuleType ruleType;
    private String grammarString = null;

    private final List<UnitField> fields = new ArrayList<>();
    private final Map<String, Integer> fieldNameCounter = new HashMap<>();

    public UnitRule(RuleName ruleName, boolean leftRecursive) {
        this.ruleName = ruleName;
        this.leftRecursive = leftRecursive;
    }

    public boolean isLeftRecursive() {
        return leftRecursive;
    }

    public String getGrammarString() {
        return grammarString;
    }

    public void setGrammarString(String s) {
        this.grammarString = s;
    }

    public RuleType getRuleType() {
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

    public RuleName getRuleName() {
        return ruleName;
    }

    public boolean isContainsList() {
        return containsList;
    }

    public void setContainsList(boolean containsList) {
        this.containsList = containsList;
    }

    public boolean isContainsTokenType() {
        return containsTokenType;
    }

    public void setContainsTokenType(boolean containsTokenType) {
        this.containsTokenType = containsTokenType;
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
