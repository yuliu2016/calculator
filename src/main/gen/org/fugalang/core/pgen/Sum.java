package org.fugalang.core.pgen;

import java.util.List;

// sum: 'term' (('+' | '-') 'term')*
public class Sum {
    public final Term term;
    public final List<Sum2Group> sum2GroupList;

    public Sum(
            Term term,
            List<Sum2Group> sum2GroupList
    ) {
        this.term = term;
        this.sum2GroupList = sum2GroupList;
    }

    // ('+' | '-') 'term'
    public static class Sum2Group {
        public final Sum2Group1Group sum2Group1Group;
        public final Term term;

        public Sum2Group(
                Sum2Group1Group sum2Group1Group,
                Term term
        ) {
            this.sum2Group1Group = sum2Group1Group;
            this.term = term;
        }
    }

    // '+' | '-'
    public static class Sum2Group1Group {
        public final boolean isTokenPlus;
        public final boolean isTokenMinus;

        public Sum2Group1Group(
                boolean isTokenPlus,
                boolean isTokenMinus
        ) {
            this.isTokenPlus = isTokenPlus;
            this.isTokenMinus = isTokenMinus;
        }
    }
}
