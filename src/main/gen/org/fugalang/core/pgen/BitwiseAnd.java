package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// bitwise_and: 'shift_expr' ('&' 'shift_expr')*
public final class BitwiseAnd extends ConjunctionRule {
    public static final String RULE_NAME = "bitwise_and";

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
        setExplicitName(RULE_NAME);
        addRequired("shiftExpr", shiftExpr);
        addRequired("bitwiseAnd2List", bitwiseAnd2List);
    }

    public ShiftExpr shiftExpr() {
        return shiftExpr;
    }

    public List<BitwiseAnd2> bitwiseAnd2List() {
        return bitwiseAnd2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        var result = false;
        parseTree.exit(level, marker, result);
        return result;
    }

    // '&' 'shift_expr'
    public static final class BitwiseAnd2 extends ConjunctionRule {
        public static final String RULE_NAME = "bitwise_and:2";

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
            setImpliedName(RULE_NAME);
            addRequired("isTokenBitAnd", isTokenBitAnd);
            addRequired("shiftExpr", shiftExpr);
        }

        public boolean isTokenBitAnd() {
            return isTokenBitAnd;
        }

        public ShiftExpr shiftExpr() {
            return shiftExpr;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            var result = false;
            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
