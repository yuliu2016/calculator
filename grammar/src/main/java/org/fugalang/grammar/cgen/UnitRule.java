package org.fugalang.grammar.cgen;

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
}
