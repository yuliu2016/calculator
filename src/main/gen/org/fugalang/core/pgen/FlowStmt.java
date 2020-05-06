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

    @Override
    protected void buildRule() {
        addChoice(breakStmt());
        addChoice(continueStmt());
        addChoice(returnStmt());
        addChoice(raiseStmt());
    }

    public BreakStmt breakStmt() {
        var element = getItem(0);
        element.failIfAbsent(BreakStmt.RULE);
        return BreakStmt.of(element);
    }

    public BreakStmt breakStmtOrNull() {
        var element = getItem(0);
        if (!element.isPresent(BreakStmt.RULE)) {
            return null;
        }
        return BreakStmt.of(element);
    }

    public boolean hasBreakStmt() {
        var element = getItem(0);
        return element.isPresent(BreakStmt.RULE);
    }

    public ContinueStmt continueStmt() {
        var element = getItem(1);
        element.failIfAbsent(ContinueStmt.RULE);
        return ContinueStmt.of(element);
    }

    public ContinueStmt continueStmtOrNull() {
        var element = getItem(1);
        if (!element.isPresent(ContinueStmt.RULE)) {
            return null;
        }
        return ContinueStmt.of(element);
    }

    public boolean hasContinueStmt() {
        var element = getItem(1);
        return element.isPresent(ContinueStmt.RULE);
    }

    public ReturnStmt returnStmt() {
        var element = getItem(2);
        element.failIfAbsent(ReturnStmt.RULE);
        return ReturnStmt.of(element);
    }

    public ReturnStmt returnStmtOrNull() {
        var element = getItem(2);
        if (!element.isPresent(ReturnStmt.RULE)) {
            return null;
        }
        return ReturnStmt.of(element);
    }

    public boolean hasReturnStmt() {
        var element = getItem(2);
        return element.isPresent(ReturnStmt.RULE);
    }

    public RaiseStmt raiseStmt() {
        var element = getItem(3);
        element.failIfAbsent(RaiseStmt.RULE);
        return RaiseStmt.of(element);
    }

    public RaiseStmt raiseStmtOrNull() {
        var element = getItem(3);
        if (!element.isPresent(RaiseStmt.RULE)) {
            return null;
        }
        return RaiseStmt.of(element);
    }

    public boolean hasRaiseStmt() {
        var element = getItem(3);
        return element.isPresent(RaiseStmt.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = BreakStmt.parse(parseTree, level + 1);
        result = result || ContinueStmt.parse(parseTree, level + 1);
        result = result || ReturnStmt.parse(parseTree, level + 1);
        result = result || RaiseStmt.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
