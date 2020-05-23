package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * bitwise_and: 'shift_expr' ('&' 'shift_expr')*
 */
public final class BitwiseAnd extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("bitwise_and", RuleType.Conjunction);

    public static BitwiseAnd of(ParseTreeNode node) {
        return new BitwiseAnd(node);
    }

    private BitwiseAnd(ParseTreeNode node) {
        super(RULE, node);
    }

    public ShiftExpr shiftExpr() {
        return get(0, ShiftExpr::of);
    }

    public List<BitwiseAnd2> shiftExprs() {
        return getList(1, BitwiseAnd2::of);
    }

    /**
     * '&' 'shift_expr'
     */
    public static final class BitwiseAnd2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("bitwise_and:2", RuleType.Conjunction);

        public static BitwiseAnd2 of(ParseTreeNode node) {
            return new BitwiseAnd2(node);
        }

        private BitwiseAnd2(ParseTreeNode node) {
            super(RULE, node);
        }

        public ShiftExpr shiftExpr() {
            return get(1, ShiftExpr::of);
        }
    }
}
