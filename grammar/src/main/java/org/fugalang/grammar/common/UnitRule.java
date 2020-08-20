package org.fugalang.grammar.common;

import org.fugalang.core.parser.RuleType;

/**
 * The simplest unit of a rule, corresponding to
 * {@link org.fugalang.grammar.classbuilder.ClassBuilder}
 * <p>
 * This means the generated code has at most one pair
 * of ENTER_FRAME/EXIT_FRAME calls.
 * <p>
 * It might have another function to gather loop functions
 */
public class UnitRule {
    private boolean containsList;
    private boolean containsTokenType;

    public boolean isLeftRecursive() {
        return false;
    }

    public void generateParsingFunction(StringBuilder sb, boolean isNamedRule) {

    }

    public void setHeaderComments(String s) {

    }

    public void setRuleType(RuleType ruleType) {

    }

    public void guardMatchEmptyString() {
    }

    public RuleName getRuleName() {
        return null;
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

    }
}
