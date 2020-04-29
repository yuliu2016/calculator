package org.fugalang.core.pgen;

// 'or' 'conjunction'
public class Disjunction2Group {
    public final boolean isTokenOr;
    public final Conjunction conjunction;

    public Disjunction2Group(
            boolean isTokenOr,
            Conjunction conjunction
    ) {
        this.isTokenOr = isTokenOr;
        this.conjunction = conjunction;
    }
}
