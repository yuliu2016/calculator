package org.fugalang.core.pgen;

// comp_iter: 'comp_for' | 'comp_if'
public class CompIter {
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
