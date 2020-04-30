package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// disjunction: 'conjunction' ('or' 'conjunction')*
public final class Disjunction extends ConjunctionRule {
    private final Conjunction conjunction;
    private final List<Disjunction2> disjunction2List;

    public Disjunction(
            Conjunction conjunction,
            List<Disjunction2> disjunction2List
    ) {
        this.conjunction = conjunction;
        this.disjunction2List = disjunction2List;

        addRequired("conjunction", conjunction);
        addRequired("disjunction2List", disjunction2List);
    }

    public Conjunction conjunction() {
        return conjunction;
    }

    public List<Disjunction2> disjunction2List() {
        return disjunction2List;
    }

    // 'or' 'conjunction'
    public static final class Disjunction2 extends ConjunctionRule {
        private final boolean isTokenOr;
        private final Conjunction conjunction;

        public Disjunction2(
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
