package org.fugalang.core.pgen;

import java.util.List;

// sum: 'term' (('+' | '-') 'term')*
public class Sum {
    private final Term term;
    private final List<Sum2Group> sum2GroupList;

    public Sum(
            Term term,
            List<Sum2Group> sum2GroupList
    ) {
        this.term = term;
        this.sum2GroupList = sum2GroupList;
    }

    public Term getTerm() {
        return term;
    }

    public List<Sum2Group> getSum2GroupList() {
        return sum2GroupList;
    }

    // ('+' | '-') 'term'
    public static class Sum2Group {
        private final Sum2Group1Group sum2Group1Group;
        private final Term term;

        public Sum2Group(
                Sum2Group1Group sum2Group1Group,
                Term term
        ) {
            this.sum2Group1Group = sum2Group1Group;
            this.term = term;
        }

        public Sum2Group1Group getSum2Group1Group() {
            return sum2Group1Group;
        }

        public Term getTerm() {
            return term;
        }
    }

    // '+' | '-'
    public static class Sum2Group1Group {
        private final boolean isTokenPlus;
        private final boolean isTokenMinus;

        public Sum2Group1Group(
                boolean isTokenPlus,
                boolean isTokenMinus
        ) {
            this.isTokenPlus = isTokenPlus;
            this.isTokenMinus = isTokenMinus;
        }

        public boolean getIsTokenPlus() {
            return isTokenPlus;
        }

        public boolean getIsTokenMinus() {
            return isTokenMinus;
        }
    }
}
