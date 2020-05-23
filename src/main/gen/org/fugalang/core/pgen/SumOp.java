package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * sum_op: '+' | '-'
 */
public final class SumOp extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("sum_op", RuleType.Disjunction);

    public static SumOp of(ParseTreeNode node) {
        return new SumOp(node);
    }

    private SumOp(ParseTreeNode node) {
        super(RULE, node);
    }

    public boolean isPlus() {
        return is(0);
    }

    public boolean isMinus() {
        return is(1);
    }
}
