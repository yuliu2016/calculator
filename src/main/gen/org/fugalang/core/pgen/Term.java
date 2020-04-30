package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// term: 'factor' (('*' | '@' | '/' | '%' | '//') 'factor')*
public final class Term extends ConjunctionRule {
    public static final String RULE_NAME = "term";

    private final Factor factor;
    private final List<Term2> term2List;

    public Term(
            Factor factor,
            List<Term2> term2List
    ) {
        this.factor = factor;
        this.term2List = term2List;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("factor", factor);
        addRequired("term2List", term2List);
    }

    public Factor factor() {
        return factor;
    }

    public List<Term2> term2List() {
        return term2List;
    }

    // ('*' | '@' | '/' | '%' | '//') 'factor'
    public static final class Term2 extends ConjunctionRule {
        public static final String RULE_NAME = "term:2";

        private final Term21 term21;
        private final Factor factor;

        public Term2(
                Term21 term21,
                Factor factor
        ) {
            this.term21 = term21;
            this.factor = factor;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("term21", term21);
            addRequired("factor", factor);
        }

        public Term21 term21() {
            return term21;
        }

        public Factor factor() {
            return factor;
        }
    }

    // '*' | '@' | '/' | '%' | '//'
    public static final class Term21 extends DisjunctionRule {
        public static final String RULE_NAME = "term:2:1";

        private final boolean isTokenTimes;
        private final boolean isTokenMatrixTimes;
        private final boolean isTokenDiv;
        private final boolean isTokenModulus;
        private final boolean isTokenFloorDiv;

        public Term21(
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

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
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
