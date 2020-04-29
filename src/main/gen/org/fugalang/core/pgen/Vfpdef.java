package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;

// vfpdef: 'NAME'
public final class Vfpdef extends ConjunctionRule {
    private final Object name;

    public Vfpdef(
            Object name
    ) {
        this.name = name;
    }

    public Object getName() {
        return name;
    }
}
