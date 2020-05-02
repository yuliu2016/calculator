package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * exprlist_comp: 'expr_or_star' ('comp_for' | (',' 'expr_or_star')* [','])
 */
public final class ExprlistComp extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("exprlist_comp", RuleType.Conjunction, true);

    private final ExprOrStar exprOrStar;
    private final ExprlistComp2 exprlistComp2;

    public ExprlistComp(
            ExprOrStar exprOrStar,
            ExprlistComp2 exprlistComp2
    ) {
        this.exprOrStar = exprOrStar;
        this.exprlistComp2 = exprlistComp2;
    }

    @Override
    protected void buildRule() {
        addRequired("exprOrStar", exprOrStar());
        addRequired("exprlistComp2", exprlistComp2());
    }

    public ExprOrStar exprOrStar() {
        return exprOrStar;
    }

    public ExprlistComp2 exprlistComp2() {
        return exprlistComp2;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = ExprOrStar.parse(parseTree, level + 1);
        result = result && ExprlistComp2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'comp_for' | (',' 'expr_or_star')* [',']
     */
    public static final class ExprlistComp2 extends DisjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("exprlist_comp:2", RuleType.Disjunction, false);

        private final CompFor compFor;
        private final ExprlistComp22 exprlistComp22;

        public ExprlistComp2(
                CompFor compFor,
                ExprlistComp22 exprlistComp22
        ) {
            this.compFor = compFor;
            this.exprlistComp22 = exprlistComp22;
        }

        @Override
        protected void buildRule() {
            addChoice("compFor", compFor());
            addChoice("exprlistComp22", exprlistComp22());
        }

        public CompFor compFor() {
            return compFor;
        }

        public boolean hasCompFor() {
            return compFor() != null;
        }

        public ExprlistComp22 exprlistComp22() {
            return exprlistComp22;
        }

        public boolean hasExprlistComp22() {
            return exprlistComp22() != null;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = CompFor.parse(parseTree, level + 1);
            result = result || ExprlistComp22.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * (',' 'expr_or_star')* [',']
     */
    public static final class ExprlistComp22 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("exprlist_comp:2:2", RuleType.Conjunction, false);

        private final List<ExprlistComp221> exprlistComp221List;
        private final boolean isTokenComma;

        public ExprlistComp22(
                List<ExprlistComp221> exprlistComp221List,
                boolean isTokenComma
        ) {
            this.exprlistComp221List = exprlistComp221List;
            this.isTokenComma = isTokenComma;
        }

        @Override
        protected void buildRule() {
            addRequired("exprlistComp221List", exprlistComp221List());
            addRequired("isTokenComma", isTokenComma());
        }

        public List<ExprlistComp221> exprlistComp221List() {
            return exprlistComp221List;
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            parseTree.enterCollection();
            while (true) {
                var pos = parseTree.position();
                if (!ExprlistComp221.parse(parseTree, level + 1) ||
                        parseTree.guardLoopExit(pos)) {
                    break;
                }
            }
            parseTree.exitCollection();
            result = parseTree.consumeTokenLiteral(",");

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * ',' 'expr_or_star'
     */
    public static final class ExprlistComp221 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("exprlist_comp:2:2:1", RuleType.Conjunction, false);

        private final boolean isTokenComma;
        private final ExprOrStar exprOrStar;

        public ExprlistComp221(
                boolean isTokenComma,
                ExprOrStar exprOrStar
        ) {
            this.isTokenComma = isTokenComma;
            this.exprOrStar = exprOrStar;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenComma", isTokenComma());
            addRequired("exprOrStar", exprOrStar());
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public ExprOrStar exprOrStar() {
            return exprOrStar;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral(",");
            result = result && ExprOrStar.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
