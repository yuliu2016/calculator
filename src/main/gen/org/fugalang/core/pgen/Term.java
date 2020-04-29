package org.fugalang.core.pgen;

import java.util.List;

// term: 'factor' (('*' | '@' | '/' | '%' | '//') 'factor')*
public class Term {
    private final Factor factor;
    private final List<Term2Group> term2GroupList;

    public Term(
            Factor factor,
            List<Term2Group> term2GroupList
    ) {
        this.factor = factor;
        this.term2GroupList = term2GroupList;
    }

    public Factor getFactor() {
        return factor;
    }

    public List<Term2Group> getTerm2GroupList() {
        return term2GroupList;
    }

    // ('*' | '@' | '/' | '%' | '//') 'factor'
    public static class Term2Group {
        private final Term2Group1Group term2Group1Group;
        private final Factor factor;

        public Term2Group(
                Term2Group1Group term2Group1Group,
                Factor factor
        ) {
            this.term2Group1Group = term2Group1Group;
            this.factor = factor;
        }

        public Term2Group1Group getTerm2Group1Group() {
            return term2Group1Group;
        }

        public Factor getFactor() {
            return factor;
        }
    }

    // '*' | '@' | '/' | '%' | '//'
    public static class Term2Group1Group {
        private final boolean isTokenTimes;
        private final boolean isTokenMatrixTimes;
        private final boolean isTokenDiv;
        private final boolean isTokenModulus;
        private final boolean isTokenFloorDiv;

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

        public boolean getIsTokenTimes() {
            return isTokenTimes;
        }

        public boolean getIsTokenMatrixTimes() {
            return isTokenMatrixTimes;
        }

        public boolean getIsTokenDiv() {
            return isTokenDiv;
        }

        public boolean getIsTokenModulus() {
            return isTokenModulus;
        }

        public boolean getIsTokenFloorDiv() {
            return isTokenFloorDiv;
        }
    }
}
