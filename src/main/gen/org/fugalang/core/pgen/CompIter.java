package org.fugalang.core.pgen;

// comp_iter: 'comp_for' | 'comp_if'
public class CompIter {
    public final CompFor compFor;
    public final CompIf compIf;

    public CompIter(
            CompFor compFor,
            CompIf compIf
    ) {
        this.compFor = compFor;
        this.compIf = compIf;
    }
}
