package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// term: 'factor' (('*' | '@' | '/' | '%' | '//') 'factor')*
public final class Term extends ConjunctionRule {
    private final Factor factor;
    private final List<Term2Group> term2GroupList;

    public Term(
            Factor factor,
            List<Term2Group> term2GroupList
    ) {
        this.factor = factor;
        this.term2GroupList = term2GroupList;

        addRequired("factor", factor);
        addRequired("term2GroupList", term2GroupList);
    }

    public Factor factor() {
        return factor;
    }

    public List<Term2Group> term2GroupList() {
        return term2GroupList;
    }

    // ('*' | '@' | '/' | '%' | '//') 'factor'
    public static final class Term2Group extends ConjunctionRule {
        private final Term2Group1Group term2Group1Group;
        private final Factor factor;

        public Term2Group(
                Term2Group1Group term2Group1Group,
                Factor factor
        ) {
            this.term2Group1Group = term2Group1Group;
            this.factor = factor;

            addRequired("term2Group1Group", term2Group1Group);
            addRequired("factor", factor);
        }

        public Term2Group1Group term2Group1Group() {
            return term2Group1Group;
        }

        public Factor factor() {
            return factor;
        }
    }

    // '*' | '@' | '/' | '%' | '//'
    public static final class Term2Group1Group extends DisjunctionRule {
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

            addChoice("isTokenTimes", isTokenTimes);
            addChoice("isTokenMatrixTimes", isTokenMatrixTimes);
            addChoice("isTokenDiv", isTokenDiv);
            addChoice("isTokenModulus", isTokenModulus);
            addChoice("isTokenFloorDiv", isTokenFloorDiv);
        }

        public boolean isTokenTimes() {
            return isTokenTimes;
        }

        public boolean isTokenMatrixTimes() {
            return isTokenMatrixTimes;
        }

        public boolean isTokenDiv() {
            return isTokenDiv;
        }

        public boolean isTokenModulus() {
            return isTokenModulus;
        }

        public boolean isTokenFloorDiv() {
            return isTokenFloorDiv;
        }
    }
}
