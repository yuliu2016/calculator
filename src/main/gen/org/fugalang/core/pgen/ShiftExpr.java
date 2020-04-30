package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// shift_expr: 'sum' (('<<' | '>>') 'sum')*
public final class ShiftExpr extends ConjunctionRule {
    public static final String RULE_NAME = "shift_expr";

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
        setExplicitName(RULE_NAME);
        addRequired("sum", sum);
        addRequired("shiftExpr2List", shiftExpr2List);
    }

    public Sum sum() {
        return sum;
    }

    public List<ShiftExpr2> shiftExpr2List() {
        return shiftExpr2List;
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

    // ('<<' | '>>') 'sum'
    public static final class ShiftExpr2 extends ConjunctionRule {
        public static final String RULE_NAME = "shift_expr:2";

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
            setImpliedName(RULE_NAME);
            addRequired("shiftExpr21", shiftExpr21);
            addRequired("sum", sum);
        }

        public ShiftExpr21 shiftExpr21() {
            return shiftExpr21;
        }

        public Sum sum() {
            return sum;
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

    // '<<' | '>>'
    public static final class ShiftExpr21 extends DisjunctionRule {
        public static final String RULE_NAME = "shift_expr:2:1";

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
            setImpliedName(RULE_NAME);
            addChoice("isTokenLshift", isTokenLshift);
            addChoice("isTokenRshift", isTokenRshift);
        }

        public boolean isTokenLshift() {
            return isTokenLshift;
        }

        public boolean isTokenRshift() {
            return isTokenRshift;
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
