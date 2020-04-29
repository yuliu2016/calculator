package org.fugalang.core.pgen;

// term: 'factor' (('*' | '@' | '/' | '%' | '//') 'factor')*
public class Term {
    public final Factor factor;
    public final Term2Group term2Group;

    public Term(
            Factor factor,
            Term2Group term2Group
    ) {
        this.factor = factor;
        this.term2Group = term2Group;
    }
}
