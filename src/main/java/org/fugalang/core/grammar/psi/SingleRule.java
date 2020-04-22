package org.fugalang.core.grammar.psi;

public class SingleRule {
    public final String name;
    public final OrRule orRule;

    public SingleRule(String name, OrRule orRule) {
        this.name = name;
        this.orRule = orRule;
    }
}
