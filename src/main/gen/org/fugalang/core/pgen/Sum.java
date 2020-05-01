package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
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

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = Term.parse(parseTree, level + 1);
        while (true) {
            if (!Sum2.parse(parseTree, level + 1)) {
                break;
            }
        }

        parseTree.exit(level, marker, result);
        return result;
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

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = Sum21.parse(parseTree, level + 1);
            result = result && Term.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
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

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("+");
            if (!result) result = parseTree.consumeTokenLiteral("-");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
