package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;

// vfpdef: 'NAME'
public final class Vfpdef extends ConjunctionRule {
    public static final String RULE_NAME = "vfpdef";

    private final String name;

    public Vfpdef(
            String name
    ) {
        this.name = name;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("name", name);
    }

    public String name() {
        return name;
    }
}
