package org.fugalang.grammar.common;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RuleSet {
    private final List<NamedRule> namedRules;
    private final Map<String, TokenEntry> tokenMap;
    private NamedRule currentRule;
    private int ruleIndexCounter = 0;

    public RuleSet(Map<String, TokenEntry> tokenMap) {
        this.tokenMap = tokenMap;
        this.namedRules = new ArrayList<>();
    }

    public List<NamedRule> getNamedRules() {
        return namedRules;
    }

    public Map<String, TokenEntry> getTokenMap() {
        return tokenMap;
    }

    public UnitRule createNamedRule(RuleName ruleName, boolean leftRecursive) {
        var dupError = false;
        for (var namedRule : namedRules) {
            if (namedRule.getRoot().getRuleName().compareExact(ruleName)) {
                dupError = true;
                break;
            }
        }

        if (dupError) {
            throw new IllegalStateException("Duplicate named rule: " + ruleName);
        }

        var unit = new UnitRule(++ruleIndexCounter, ruleName, leftRecursive);

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
            if (builder.getRuleName().compareExact(ruleName)) {
                dupError = true;
                break;
            }
        }

        if (dupError) {
            throw new IllegalStateException("Duplicate inner rule: " + ruleName);
        }

        var unit = new UnitRule(++ruleIndexCounter, ruleName, false);

        current.getComponents().add(unit);

        return unit;
    }

}
