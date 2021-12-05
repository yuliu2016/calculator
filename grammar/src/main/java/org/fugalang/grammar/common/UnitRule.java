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
    private ResultClause resultClause;
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

    public ResultClause resultClause() {
        return resultClause;
    }

    public void setResultClause(ResultClause resultClause) {
        this.resultClause = resultClause;
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
        var fieldName = field.fieldName();

        // field name conflict resolution
        // eg. rule: subrule subrule would be made into
        // subrule and subrule1, respectively
        String name = fieldName.pluralized();
        if (fieldNameCounter.containsKey(name)) {
            int cnt = fieldNameCounter.get(name) + 1;
            fieldNameCounter.put(name, cnt);

            // modify the field name with the field count
            fields.add(new UnitField(
                    field.ruleName(),
                    field.fieldName().withSuffix(cnt),
                    field.fieldType(),
                    field.resultSource(),
                    field.delimiter(),
                    field.resultClause()
            ));
        } else {
            fieldNameCounter.put(name, 0);
            fields.add(field);
        }
    }
}
