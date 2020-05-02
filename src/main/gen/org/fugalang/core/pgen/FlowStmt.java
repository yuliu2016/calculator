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
        addChoice("breakStmt", breakStmt());
        addChoice("continueStmt", continueStmt());
        addChoice("returnStmt", returnStmt());
        addChoice("raiseStmt", raiseStmt());
    }

    public BreakStmt breakStmt() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return BreakStmt.of(element);
    }

    public boolean hasBreakStmt() {
        return breakStmt() != null;
    }

    public ContinueStmt continueStmt() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return ContinueStmt.of(element);
    }

    public boolean hasContinueStmt() {
        return continueStmt() != null;
    }

    public ReturnStmt returnStmt() {
        var element = getItem(2);
        if (!element.isPresent()) return null;
        return ReturnStmt.of(element);
    }

    public boolean hasReturnStmt() {
        return returnStmt() != null;
    }

    public RaiseStmt raiseStmt() {
        var element = getItem(3);
        if (!element.isPresent()) return null;
        return RaiseStmt.of(element);
    }

    public boolean hasRaiseStmt() {
        return raiseStmt() != null;
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