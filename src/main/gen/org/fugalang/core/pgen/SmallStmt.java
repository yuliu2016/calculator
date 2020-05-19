package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * small_stmt: 'pass' | 'break' | 'continue' | 'del_stmt' | 'return_stmt' | 'raise_stmt' | 'nonlocal_stmt' | 'assert_stmt' | 'import_name' | 'import_from' | 'assignment'
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

    public boolean isPass() {
        return is(0);
    }

    public boolean isBreak() {
        return is(1);
    }

    public boolean isContinue() {
        return is(2);
    }

    public DelStmt delStmt() {
        return get(3, DelStmt::of);
    }

    public boolean hasDelStmt() {
        return has(3, DelStmt.RULE);
    }

    public ReturnStmt returnStmt() {
        return get(4, ReturnStmt::of);
    }

    public boolean hasReturnStmt() {
        return has(4, ReturnStmt.RULE);
    }

    public RaiseStmt raiseStmt() {
        return get(5, RaiseStmt::of);
    }

    public boolean hasRaiseStmt() {
        return has(5, RaiseStmt.RULE);
    }

    public NonlocalStmt nonlocalStmt() {
        return get(6, NonlocalStmt::of);
    }

    public boolean hasNonlocalStmt() {
        return has(6, NonlocalStmt.RULE);
    }

    public AssertStmt assertStmt() {
        return get(7, AssertStmt::of);
    }

    public boolean hasAssertStmt() {
        return has(7, AssertStmt.RULE);
    }

    public ImportName importName() {
        return get(8, ImportName::of);
    }

    public boolean hasImportName() {
        return has(8, ImportName.RULE);
    }

    public ImportFrom importFrom() {
        return get(9, ImportFrom::of);
    }

    public boolean hasImportFrom() {
        return has(9, ImportFrom.RULE);
    }

    public Assignment assignment() {
        return get(10, Assignment::of);
    }

    public boolean hasAssignment() {
        return has(10, Assignment.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("pass");
        r = r || t.consume("break");
        r = r || t.consume("continue");
        r = r || DelStmt.parse(t, lv + 1);
        r = r || ReturnStmt.parse(t, lv + 1);
        r = r || RaiseStmt.parse(t, lv + 1);
        r = r || NonlocalStmt.parse(t, lv + 1);
        r = r || AssertStmt.parse(t, lv + 1);
        r = r || ImportName.parse(t, lv + 1);
        r = r || ImportFrom.parse(t, lv + 1);
        r = r || Assignment.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
