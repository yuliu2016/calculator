package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// sum: 'term' (('+' | '-') 'term')*
public final class Sum extends ConjunctionRule {
    public static final String RULE_NAME = "sum";

    private final Term term;
    private final List<Sum2> sum2List;

    public Sum(
            Term term,
            List<Sum2> sum2List
    ) {
        this.term = term;
        this.sum2List = sum2List;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("term", term);
        addRequired("sum2List", sum2List);
    }

    public Term term() {
        return term;
    }

    public List<Sum2> sum2List() {
        return sum2List;
    }

    // ('+' | '-') 'term'
    public static final class Sum2 extends ConjunctionRule {
        public static final String RULE_NAME = "sum:2";

        private final Sum21 sum21;
        private final Term term;

        public Sum2(
                Sum21 sum21,
                Term term
        ) {
            this.sum21 = sum21;
            this.term = term;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("sum21", sum21);
            addRequired("term", term);
        }

        public Sum21 sum21() {
            return sum21;
        }

        public Term term() {
            return term;
        }
    }

    // '+' | '-'
    public static final class Sum21 extends DisjunctionRule {
        public static final String RULE_NAME = "sum:2:1";

        private final boolean isTokenPlus;
        private final boolean isTokenMinus;

        public Sum21(
                boolean isTokenPlus,
                boolean isTokenMinus
        ) {
            this.isTokenPlus = isTokenPlus;
            this.isTokenMinus = isTokenMinus;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addChoice("isTokenPlus", isTokenPlus);
            addChoice("isTokenMinus", isTokenMinus);
        }

        public boolean isTokenPlus() {
            return isTokenPlus;
        }

        public boolean isTokenMinus() {
            return isTokenMinus;
        }
    }
}
