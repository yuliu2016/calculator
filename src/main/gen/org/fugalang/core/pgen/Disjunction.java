package org.fugalang.core.pgen;

import java.util.List;

// disjunction: 'conjunction' ('or' 'conjunction')*
public class Disjunction {
    private final Conjunction conjunction;
    private final List<Disjunction2Group> disjunction2GroupList;

    public Disjunction(
            Conjunction conjunction,
            List<Disjunction2Group> disjunction2GroupList
    ) {
        this.conjunction = conjunction;
        this.disjunction2GroupList = disjunction2GroupList;
    }

    public Conjunction getConjunction() {
        return conjunction;
    }

    public List<Disjunction2Group> getDisjunction2GroupList() {
        return disjunction2GroupList;
    }

    // 'or' 'conjunction'
    public static class Disjunction2Group {
        private final boolean isTokenOr;
        private final Conjunction conjunction;

        public Disjunction2Group(
                boolean isTokenOr,
                Conjunction conjunction
        ) {
            this.isTokenOr = isTokenOr;
            this.conjunction = conjunction;
        }

        public boolean getIsTokenOr() {
            return isTokenOr;
        }

        public Conjunction getConjunction() {
            return conjunction;
        }
    }
}
