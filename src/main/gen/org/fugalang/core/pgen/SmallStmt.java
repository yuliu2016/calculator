package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * small_stmt: 'del_stmt' | 'pass_stmt' | 'flow_stmt' | 'import_stmt' | 'assert_stmt' | 'assignment'
 */
public final class SmallStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("small_stmt", RuleType.Disjunction);

    public static SmallStmt of(ParseTreeNode node) {
        return new SmallStmt(node);
    }

    private SmallStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public DelStmt delStmt() {
        return DelStmt.of(get(0));
    }

    public boolean hasDelStmt() {
        return has(0, DelStmt.RULE);
    }

    public PassStmt passStmt() {
        return PassStmt.of(get(1));
    }

    public boolean hasPassStmt() {
        return has(1, PassStmt.RULE);
    }

    public FlowStmt flowStmt() {
        return FlowStmt.of(get(2));
    }

    public boolean hasFlowStmt() {
        return has(2, FlowStmt.RULE);
    }

    public ImportStmt importStmt() {
        return ImportStmt.of(get(3));
    }

    public boolean hasImportStmt() {
        return has(3, ImportStmt.RULE);
    }

    public AssertStmt assertStmt() {
        return AssertStmt.of(get(4));
    }

    public boolean hasAssertStmt() {
        return has(4, AssertStmt.RULE);
    }

    public Assignment assignment() {
        return Assignment.of(get(5));
    }

    public boolean hasAssignment() {
        return has(5, Assignment.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = DelStmt.parse(t, lv + 1);
        r = r || PassStmt.parse(t, lv + 1);
        r = r || FlowStmt.parse(t, lv + 1);
        r = r || ImportStmt.parse(t, lv + 1);
        r = r || AssertStmt.parse(t, lv + 1);
        r = r || Assignment.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
