package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * shift_op: '<<' | '>>'
 */
public final class ShiftOp extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("shift_op", RuleType.Disjunction);

    public static ShiftOp of(ParseTreeNode node) {
        return new ShiftOp(node);
    }

    private ShiftOp(ParseTreeNode node) {
        super(RULE, node);
    }

    public boolean isLshift() {
        return is(0);
    }

    public boolean isRshift() {
        return is(1);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("<<");
        r = r || t.consume(">>");
        t.exit(r);
        return r;
    }
}
