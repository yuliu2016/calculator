package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// disjunction: 'conjunction' ('or' 'conjunction')*
public final class Disjunction extends ConjunctionRule {
    private final Conjunction conjunction;
    private final List<Disjunction2Group> disjunction2GroupList;

    public Disjunction(
            Conjunction conjunction,
            List<Disjunction2Group> disjunction2GroupList
    ) {
        this.conjunction = conjunction;
        this.disjunction2GroupList = disjunction2GroupList;

        addRequired("conjunction", conjunction);
        addRequired("disjunction2GroupList", disjunction2GroupList);
    }

    public Conjunction conjunction() {
        return conjunction;
    }

    public List<Disjunction2Group> disjunction2GroupList() {
        return disjunction2GroupList;
    }

    // 'or' 'conjunction'
    public static final class Disjunction2Group extends ConjunctionRule {
        private final boolean isTokenOr;
        private final Conjunction conjunction;

        public Disjunction2Group(
                boolean isTokenOr,
                Conjunction conjunction
        ) {
            this.isTokenOr = isTokenOr;
            this.conjunction = conjunction;

            addRequired("isTokenOr", isTokenOr);
            addRequired("conjunction", conjunction);
        }

        public boolean isTokenOr() {
            return isTokenOr;
        }

        public Conjunction conjunction() {
            return conjunction;
        }
    }
}
