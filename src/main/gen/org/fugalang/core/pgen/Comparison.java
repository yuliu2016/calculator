package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * comparison: 'bitwise_or' ('comp_op' 'bitwise_or')*
 */
public final class Comparison extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("comparison", RuleType.Conjunction);

    public static Comparison of(ParseTreeNode node) {
        return new Comparison(node);
    }

    private Comparison(ParseTreeNode node) {
        super(RULE, node);
    }

    public BitwiseOr bitwiseOr() {
        return get(0, BitwiseOr::of);
    }

    public List<Comparison2> compOpBitwiseOrs() {
        return getList(1, Comparison2::of);
    }

    /**
     * 'comp_op' 'bitwise_or'
     */
    public static final class Comparison2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("comparison:2", RuleType.Conjunction);

        public static Comparison2 of(ParseTreeNode node) {
            return new Comparison2(node);
        }

        private Comparison2(ParseTreeNode node) {
            super(RULE, node);
        }

        public CompOp compOp() {
            return get(0, CompOp::of);
        }

        public BitwiseOr bitwiseOr() {
            return get(1, BitwiseOr::of);
        }
    }
}
