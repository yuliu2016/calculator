package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * flow_stmt: 'break_stmt' | 'continue_stmt' | 'return_stmt' | 'raise_stmt'
 */
public final class FlowStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("flow_stmt", RuleType.Disjunction, true);

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

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = BreakStmt.parse(t, l + 1);
        r = r || ContinueStmt.parse(t, l + 1);
        r = r || ReturnStmt.parse(t, l + 1);
        r = r || RaiseStmt.parse(t, l + 1);
        t.exit(r);
        return r;
    }
}
