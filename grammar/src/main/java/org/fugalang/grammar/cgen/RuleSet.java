package org.fugalang.grammar.cgen;

import org.fugalang.grammar.common.RuleName;

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

    public UnitRule createRootClass(RuleName ruleName, boolean isLeftRecursive) {
        return null;
    }

    public UnitRule createComponentClass(RuleName ruleName) {
        return null;
    }

    public void markRootClassDone() {

    }
}
