package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * flow_stmt: 'break_stmt' | 'continue_stmt' | 'return_stmt' | 'raise_stmt'
 */
public final class FlowStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("flow_stmt", RuleType.Disjunction);

    public static FlowStmt of(ParseTreeNode node) {
        return new FlowStmt(node);
    }

    private FlowStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public BreakStmt breakStmt() {
        return get(0, BreakStmt::of);
    }

    public boolean hasBreakStmt() {
        return has(0, BreakStmt.RULE);
    }

    public ContinueStmt continueStmt() {
        return get(1, ContinueStmt::of);
    }

    public boolean hasContinueStmt() {
        return has(1, ContinueStmt.RULE);
    }

    public ReturnStmt returnStmt() {
        return get(2, ReturnStmt::of);
    }

    public boolean hasReturnStmt() {
        return has(2, ReturnStmt.RULE);
    }

    public RaiseStmt raiseStmt() {
        return get(3, RaiseStmt::of);
    }

    public boolean hasRaiseStmt() {
        return has(3, RaiseStmt.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = BreakStmt.parse(t, lv + 1);
        r = r || ContinueStmt.parse(t, lv + 1);
        r = r || ReturnStmt.parse(t, lv + 1);
        r = r || RaiseStmt.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
