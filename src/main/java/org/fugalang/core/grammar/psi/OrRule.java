package org.fugalang.core.grammar.psi;

import java.util.List;

public class OrRule {
    public final AndRule andRule;

    public final List<AndRule> andRules;

    public OrRule(AndRule andRule, List<AndRule> andRules) {
        this.andRule = andRule;
        this.andRules = andRules;
    }
}
