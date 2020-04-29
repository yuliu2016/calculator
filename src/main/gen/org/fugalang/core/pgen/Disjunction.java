package org.fugalang.core.pgen;

import java.util.List;

// disjunction: 'conjunction' ('or' 'conjunction')*
public class Disjunction {
    public final Conjunction conjunction;
    public final List<Disjunction2Group> disjunction2GroupList;

    public Disjunction(
            Conjunction conjunction,
            List<Disjunction2Group> disjunction2GroupList
    ) {
        this.conjunction = conjunction;
        this.disjunction2GroupList = disjunction2GroupList;
    }

    // 'or' 'conjunction'
    public static class Disjunction2Group {
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
}
