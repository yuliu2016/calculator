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

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("+");
        r = r || t.consume("-");
        t.exit(r);
        return r;
    }
}
