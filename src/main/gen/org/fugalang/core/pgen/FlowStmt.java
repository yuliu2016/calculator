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
        return BreakStmt.of(getItem(0));
    }

    public boolean hasBreakStmt() {
        return hasItemOfRule(0, BreakStmt.RULE);
    }

    public ContinueStmt continueStmt() {
        return ContinueStmt.of(getItem(1));
    }

    public boolean hasContinueStmt() {
        return hasItemOfRule(1, ContinueStmt.RULE);
    }

    public ReturnStmt returnStmt() {
        return ReturnStmt.of(getItem(2));
    }

    public boolean hasReturnStmt() {
        return hasItemOfRule(2, ReturnStmt.RULE);
    }

    public RaiseStmt raiseStmt() {
        return RaiseStmt.of(getItem(3));
    }

    public boolean hasRaiseStmt() {
        return hasItemOfRule(3, RaiseStmt.RULE);
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
