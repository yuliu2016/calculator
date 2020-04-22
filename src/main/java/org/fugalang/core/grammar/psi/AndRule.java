package org.fugalang.core.grammar.psi;

import java.util.List;

public class AndRule {
    public final RepeatRule repeatRule;
    public final List<RepeatRule> repeatRules;

    public AndRule(RepeatRule repeatRule, List<RepeatRule> repeatRules) {
        this.repeatRule = repeatRule;
        this.repeatRules = repeatRules;
    }
}
