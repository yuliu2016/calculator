package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * bitwise_and: 'shift_expr' ('&' 'shift_expr')*
 */
public final class BitwiseAnd extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("bitwise_and", RuleType.Conjunction, true);

    private final ShiftExpr shiftExpr;
    private final List<BitwiseAnd2> bitwiseAnd2List;

    public BitwiseAnd(
            ShiftExpr shiftExpr,
            List<BitwiseAnd2> bitwiseAnd2List
    ) {
        this.shiftExpr = shiftExpr;
        this.bitwiseAnd2List = bitwiseAnd2List;
    }

    @Override
    protected void buildRule() {
        addRequired("shiftExpr", shiftExpr());
        addRequired("bitwiseAnd2List", bitwiseAnd2List());
    }

    public ShiftExpr shiftExpr() {
        return shiftExpr;
    }

    public List<BitwiseAnd2> bitwiseAnd2List() {
        return bitwiseAnd2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = ShiftExpr.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!BitwiseAnd2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '&' 'shift_expr'
     */
    public static final class BitwiseAnd2 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("bitwise_and:2", RuleType.Conjunction, false);

        private final boolean isTokenBitAnd;
        private final ShiftExpr shiftExpr;

        public BitwiseAnd2(
                boolean isTokenBitAnd,
                ShiftExpr shiftExpr
        ) {
            this.isTokenBitAnd = isTokenBitAnd;
            this.shiftExpr = shiftExpr;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenBitAnd", isTokenBitAnd());
            addRequired("shiftExpr", shiftExpr());
        }

        public boolean isTokenBitAnd() {
            return isTokenBitAnd;
        }

        public ShiftExpr shiftExpr() {
            return shiftExpr;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("&");
            result = result && ShiftExpr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
