package org.fugalang.core.pgen;

// ('*' | '@' | '/' | '%' | '//') 'factor'
public class Term2Group {
    public final Term2Group1Group term2Group1Group;
    public final Factor factor;

    public Term2Group(
            Term2Group1Group term2Group1Group,
            Factor factor
    ) {
        this.term2Group1Group = term2Group1Group;
        this.factor = factor;
    }
}
