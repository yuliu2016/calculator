package org.fugalang.core.pgen;

// sum: 'term' (('+' | '-') 'term')*
public class Sum {
    public final Term term;
    public final Sum2Group sum2Group;

    public Sum(
            Term term,
            Sum2Group sum2Group
    ) {
        this.term = term;
        this.sum2Group = sum2Group;
    }
}
