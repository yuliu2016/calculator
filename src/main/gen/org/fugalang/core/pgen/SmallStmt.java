package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * small_stmt: 'del_stmt' | 'pass_stmt' | 'flow_stmt' | 'import_stmt' | 'assert_stmt' | 'assignment'
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

    public DelStmt delStmt() {
        return DelStmt.of(getItem(0));
    }

    public boolean hasDelStmt() {
        return hasItemOfRule(0, DelStmt.RULE);
    }

    public PassStmt passStmt() {
        return PassStmt.of(getItem(1));
    }

    public boolean hasPassStmt() {
        return hasItemOfRule(1, PassStmt.RULE);
    }

    public FlowStmt flowStmt() {
        return FlowStmt.of(getItem(2));
    }

    public boolean hasFlowStmt() {
        return hasItemOfRule(2, FlowStmt.RULE);
    }

    public ImportStmt importStmt() {
        return ImportStmt.of(getItem(3));
    }

    public boolean hasImportStmt() {
        return hasItemOfRule(3, ImportStmt.RULE);
    }

    public AssertStmt assertStmt() {
        return AssertStmt.of(getItem(4));
    }

    public boolean hasAssertStmt() {
        return hasItemOfRule(4, AssertStmt.RULE);
    }

    public Assignment assignment() {
        return Assignment.of(getItem(5));
    }

    public boolean hasAssignment() {
        return hasItemOfRule(5, Assignment.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = DelStmt.parse(parseTree, level + 1);
        result = result || PassStmt.parse(parseTree, level + 1);
        result = result || FlowStmt.parse(parseTree, level + 1);
        result = result || ImportStmt.parse(parseTree, level + 1);
        result = result || AssertStmt.parse(parseTree, level + 1);
        result = result || Assignment.parse(parseTree, level + 1);

        parseTree.exit(result);
        return result;
    }
}
