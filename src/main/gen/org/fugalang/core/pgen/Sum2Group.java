package org.fugalang.core.pgen;

// ('+' | '-') 'term'
public class Sum2Group {
    public final Sum2Group1Group sum2Group1Group;
    public final Term term;

    public Sum2Group(
            Sum2Group1Group sum2Group1Group,
            Term term
    ) {
        this.sum2Group1Group = sum2Group1Group;
        this.term = term;
    }
}
