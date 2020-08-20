package org.fugalang.grammar.common;


import java.util.ArrayList;
import java.util.List;

public class RuleSet {
    private final List<NamedRule> namedRules;
    private NamedRule currentRule;

    public RuleSet() {
        this.namedRules = new ArrayList<>();
    }

    public List<NamedRule> getNamedRules() {
        return namedRules;
    }

    public UnitRule createNamedRule(RuleName ruleName, boolean leftRecursive) {
        var dupError = false;
        for (var namedRule : namedRules) {
            if (namedRule.getRoot().getRuleName().equals(ruleName)) {
                dupError = true;
                break;
            }
        }

        if (dupError) {
            throw new IllegalStateException("Duplicate named rule: " + ruleName);
        }

        var unit = new UnitRule(ruleName, leftRecursive);

        currentRule = new NamedRule(unit);
        namedRules.add(currentRule);

        return unit;
    }


    public void namedRuleDone() {
        if (currentRule == null) {
            throw new IllegalStateException("No named rule set");
        }
        currentRule = null;
    }

    public UnitRule createUnnamedSubRule(RuleName ruleName) {
        if (currentRule == null) {
            throw new IllegalStateException("No named rule to add to");
        }

        var current = currentRule;

        var dupError = false;
        for (var builder : current.getComponents()) {
            if (builder.getRuleName().equals(ruleName)) {
                dupError = true;
                break;
            }
        }

        if (dupError) {
            throw new IllegalStateException("Duplicate inner rule: " + ruleName);
        }

        var unit = new UnitRule(ruleName, false);

        current.getComponents().add(unit);

        return unit;
    }

}
