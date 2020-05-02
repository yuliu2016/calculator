package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * set_maker: 'expr_or_star' ('comp_for' | (',' 'expr_or_star')* [','])
 */
public final class SetMaker extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("set_maker", RuleType.Conjunction, true);

    private final ExprOrStar exprOrStar;
    private final SetMaker2 setMaker2;

    public SetMaker(
            ExprOrStar exprOrStar,
            SetMaker2 setMaker2
    ) {
        this.exprOrStar = exprOrStar;
        this.setMaker2 = setMaker2;
    }

    @Override
    protected void buildRule() {
        addRequired("exprOrStar", exprOrStar());
        addRequired("setMaker2", setMaker2());
    }

    public ExprOrStar exprOrStar() {
        return exprOrStar;
    }

    public SetMaker2 setMaker2() {
        return setMaker2;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = ExprOrStar.parse(parseTree, level + 1);
        result = result && SetMaker2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'comp_for' | (',' 'expr_or_star')* [',']
     */
    public static final class SetMaker2 extends DisjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("set_maker:2", RuleType.Disjunction, false);

        private final CompFor compFor;
        private final SetMaker22 setMaker22;

        public SetMaker2(
                CompFor compFor,
                SetMaker22 setMaker22
        ) {
            this.compFor = compFor;
            this.setMaker22 = setMaker22;
        }

        @Override
        protected void buildRule() {
            addChoice("compFor", compFor());
            addChoice("setMaker22", setMaker22());
        }

        public CompFor compFor() {
            return compFor;
        }

        public boolean hasCompFor() {
            return compFor() != null;
        }

        public SetMaker22 setMaker22() {
            return setMaker22;
        }

        public boolean hasSetMaker22() {
            return setMaker22() != null;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = CompFor.parse(parseTree, level + 1);
            result = result || SetMaker22.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * (',' 'expr_or_star')* [',']
     */
    public static final class SetMaker22 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("set_maker:2:2", RuleType.Conjunction, false);

        private final List<SetMaker221> setMaker221List;
        private final boolean isTokenComma;

        public SetMaker22(
                List<SetMaker221> setMaker221List,
                boolean isTokenComma
        ) {
            this.setMaker221List = setMaker221List;
            this.isTokenComma = isTokenComma;
        }

        @Override
        protected void buildRule() {
            addRequired("setMaker221List", setMaker221List());
            addRequired("isTokenComma", isTokenComma());
        }

        public List<SetMaker221> setMaker221List() {
            return setMaker221List;
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
                if (!SetMaker221.parse(parseTree, level + 1) ||
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
    public static final class SetMaker221 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("set_maker:2:2:1", RuleType.Conjunction, false);

        private final boolean isTokenComma;
        private final ExprOrStar exprOrStar;

        public SetMaker221(
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
