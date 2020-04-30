package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;

// vfpdef: 'NAME'
public final class Vfpdef extends ConjunctionRule {
    private final String name;

    public Vfpdef(
            String name
    ) {
        this.name = name;

        addRequired("name", name);
    }

    public String name() {
        return name;
    }
}
