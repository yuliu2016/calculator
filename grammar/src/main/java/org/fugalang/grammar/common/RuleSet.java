package org.fugalang.grammar.common;

import java.util.ArrayList;
import java.util.List;

public class RuleSet {
    private final List<NamedRule> namedRules;

    public RuleSet() {
        this.namedRules = new ArrayList<>();
    }

    public List<NamedRule> getNamedRules() {
        return namedRules;
    }

    void writeToFiles() {

    }

    public UnitRule createNamedRule(RuleName ruleName, boolean isLeftRecursive) {
        return null;
    }

    public UnitRule createUnnamedSubRule(RuleName ruleName) {
        return null;
    }

    public void namedRuleDone() {

    }
}
