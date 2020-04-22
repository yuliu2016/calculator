package org.fugalang.core.grammar.psi;

public class SubRule {
    public final OrRule groupedOrRule;
    public final OrRule optionalOrRule;
    public final String token;

    public SubRule(OrRule groupedOrRule, OrRule optionalOrRule, String token) {
        this.groupedOrRule = groupedOrRule;
        this.optionalOrRule = optionalOrRule;
        this.token = token;
    }
}
