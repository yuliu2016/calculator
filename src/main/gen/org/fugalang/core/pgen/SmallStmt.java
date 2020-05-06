package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * small_stmt: 'expr_stmt' | 'del_stmt' | 'pass_stmt' | 'flow_stmt' | 'import_stmt' | 'assert_stmt'
 */
public final class SmallStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("small_stmt", RuleType.Disjunction, true);

    public static SmallStmt of(ParseTreeNode node) {
        return new SmallStmt(node);
    }

    private SmallStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addChoice(exprStmt());
        addChoice(delStmt());
        addChoice(passStmt());
        addChoice(flowStmt());
        addChoice(importStmt());
        addChoice(assertStmt());
    }

    public ExprStmt exprStmt() {
        var element = getItem(0);
        element.failIfAbsent(ExprStmt.RULE);
        return ExprStmt.of(element);
    }

    public ExprStmt exprStmtOrNull() {
        var element = getItem(0);
        if (!element.isPresent(ExprStmt.RULE)) {
            return null;
        }
        return ExprStmt.of(element);
    }

    public boolean hasExprStmt() {
        var element = getItem(0);
        return element.isPresent(ExprStmt.RULE);
    }

    public DelStmt delStmt() {
        var element = getItem(1);
        element.failIfAbsent(DelStmt.RULE);
        return DelStmt.of(element);
    }

    public DelStmt delStmtOrNull() {
        var element = getItem(1);
        if (!element.isPresent(DelStmt.RULE)) {
            return null;
        }
        return DelStmt.of(element);
    }

    public boolean hasDelStmt() {
        var element = getItem(1);
        return element.isPresent(DelStmt.RULE);
    }

    public PassStmt passStmt() {
        var element = getItem(2);
        element.failIfAbsent(PassStmt.RULE);
        return PassStmt.of(element);
    }

    public PassStmt passStmtOrNull() {
        var element = getItem(2);
        if (!element.isPresent(PassStmt.RULE)) {
            return null;
        }
        return PassStmt.of(element);
    }

    public boolean hasPassStmt() {
        var element = getItem(2);
        return element.isPresent(PassStmt.RULE);
    }

    public FlowStmt flowStmt() {
        var element = getItem(3);
        element.failIfAbsent(FlowStmt.RULE);
        return FlowStmt.of(element);
    }

    public FlowStmt flowStmtOrNull() {
        var element = getItem(3);
        if (!element.isPresent(FlowStmt.RULE)) {
            return null;
        }
        return FlowStmt.of(element);
    }

    public boolean hasFlowStmt() {
        var element = getItem(3);
        return element.isPresent(FlowStmt.RULE);
    }

    public ImportStmt importStmt() {
        var element = getItem(4);
        element.failIfAbsent(ImportStmt.RULE);
        return ImportStmt.of(element);
    }

    public ImportStmt importStmtOrNull() {
        var element = getItem(4);
        if (!element.isPresent(ImportStmt.RULE)) {
            return null;
        }
        return ImportStmt.of(element);
    }

    public boolean hasImportStmt() {
        var element = getItem(4);
        return element.isPresent(ImportStmt.RULE);
    }

    public AssertStmt assertStmt() {
        var element = getItem(5);
        element.failIfAbsent(AssertStmt.RULE);
        return AssertStmt.of(element);
    }

    public AssertStmt assertStmtOrNull() {
        var element = getItem(5);
        if (!element.isPresent(AssertStmt.RULE)) {
            return null;
        }
        return AssertStmt.of(element);
    }

    public boolean hasAssertStmt() {
        var element = getItem(5);
        return element.isPresent(AssertStmt.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = ExprStmt.parse(parseTree, level + 1);
        result = result || DelStmt.parse(parseTree, level + 1);
        result = result || PassStmt.parse(parseTree, level + 1);
        result = result || FlowStmt.parse(parseTree, level + 1);
        result = result || ImportStmt.parse(parseTree, level + 1);
        result = result || AssertStmt.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
