package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * shift_expr: 'sum' (('<<' | '>>') 'sum')*
 */
public final class ShiftExpr extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("shift_expr", RuleType.Conjunction, true);

    private final Sum sum;
    private final List<ShiftExpr2> shiftExpr2List;

    public ShiftExpr(
            Sum sum,
            List<ShiftExpr2> shiftExpr2List
    ) {
        this.sum = sum;
        this.shiftExpr2List = shiftExpr2List;
    }

    @Override
    protected void buildRule() {
        addRequired("sum", sum());
        addRequired("shiftExpr2List", shiftExpr2List());
    }

    public Sum sum() {
        return sum;
    }

    public List<ShiftExpr2> shiftExpr2List() {
        return shiftExpr2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Sum.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!ShiftExpr2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ('<<' | '>>') 'sum'
     */
    public static final class ShiftExpr2 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("shift_expr:2", RuleType.Conjunction, false);

        private final ShiftExpr21 shiftExpr21;
        private final Sum sum;

        public ShiftExpr2(
                ShiftExpr21 shiftExpr21,
                Sum sum
        ) {
            this.shiftExpr21 = shiftExpr21;
            this.sum = sum;
        }

        @Override
        protected void buildRule() {
            addRequired("shiftExpr21", shiftExpr21());
            addRequired("sum", sum());
        }

        public ShiftExpr21 shiftExpr21() {
            return shiftExpr21;
        }

        public Sum sum() {
            return sum;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = ShiftExpr21.parse(parseTree, level + 1);
            result = result && Sum.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * '<<' | '>>'
     */
    public static final class ShiftExpr21 extends DisjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("shift_expr:2:1", RuleType.Disjunction, false);

        private final boolean isTokenLshift;
        private final boolean isTokenRshift;

        public ShiftExpr21(
                boolean isTokenLshift,
                boolean isTokenRshift
        ) {
            this.isTokenLshift = isTokenLshift;
            this.isTokenRshift = isTokenRshift;
        }

        @Override
        protected void buildRule() {
            addChoice("isTokenLshift", isTokenLshift());
            addChoice("isTokenRshift", isTokenRshift());
        }

        public boolean isTokenLshift() {
            return isTokenLshift;
        }

        public boolean isTokenRshift() {
            return isTokenRshift;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("<<");
            result = result || parseTree.consumeTokenLiteral(">>");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
