package org.fugalang.core.pgen;

import org.fugalang.core.parser.DisjunctionRule;

// comp_iter: 'comp_for' | 'comp_if'
public final class CompIter extends DisjunctionRule {
    private final CompFor compFor;
    private final CompIf compIf;

    public CompIter(
            CompFor compFor,
            CompIf compIf
    ) {
        this.compFor = compFor;
        this.compIf = compIf;
    }

    public CompFor getCompFor() {
        return compFor;
    }

    public CompIf getCompIf() {
        return compIf;
    }
}
