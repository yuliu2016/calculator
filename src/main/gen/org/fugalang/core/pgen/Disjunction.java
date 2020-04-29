package org.fugalang.core.pgen;

// disjunction: 'conjunction' ('or' 'conjunction')*
public class Disjunction {
    public final Conjunction conjunction;
    public final Disjunction2Group disjunction2Group;

    public Disjunction(
            Conjunction conjunction,
            Disjunction2Group disjunction2Group
    ) {
        this.conjunction = conjunction;
        this.disjunction2Group = disjunction2Group;
    }
}
