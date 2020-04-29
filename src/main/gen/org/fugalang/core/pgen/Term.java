package org.fugalang.core.pgen;

import java.util.List;

// term: 'factor' (('*' | '@' | '/' | '%' | '//') 'factor')*
public class Term {
    public final Factor factor;
    public final List<Term2Group> term2GroupList;

    public Term(
            Factor factor,
            List<Term2Group> term2GroupList
    ) {
        this.factor = factor;
        this.term2GroupList = term2GroupList;
    }

    // ('*' | '@' | '/' | '%' | '//') 'factor'
    public static class Term2Group {
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

    // '*' | '@' | '/' | '%' | '//'
    public static class Term2Group1Group {
        public final boolean isTokenTimes;
        public final boolean isTokenMatrixTimes;
        public final boolean isTokenDiv;
        public final boolean isTokenModulus;
        public final boolean isTokenFloorDiv;

        public Term2Group1Group(
                boolean isTokenTimes,
                boolean isTokenMatrixTimes,
                boolean isTokenDiv,
                boolean isTokenModulus,
                boolean isTokenFloorDiv
        ) {
            this.isTokenTimes = isTokenTimes;
            this.isTokenMatrixTimes = isTokenMatrixTimes;
            this.isTokenDiv = isTokenDiv;
            this.isTokenModulus = isTokenModulus;
            this.isTokenFloorDiv = isTokenFloorDiv;
        }
    }
}
