package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * small_stmt: 'pass' | 'break' | 'continue' | 'del_stmt' | 'return_stmt' | 'raise_stmt' | 'nonlocal_stmt' | 'assert_stmt' | 'import_name' | 'import_from' | 'assignment'
 */
public final class SmallStmt extends NodeWrapper {

    public SmallStmt(ParseTreeNode node) {
        super(ParserRules.SMALL_STMT, node);
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
        return get(3, DelStmt.class);
    }

    public boolean hasDelStmt() {
        return has(3);
    }

    public ReturnStmt returnStmt() {
        return get(4, ReturnStmt.class);
    }

    public boolean hasReturnStmt() {
        return has(4);
    }

    public RaiseStmt raiseStmt() {
        return get(5, RaiseStmt.class);
    }

    public boolean hasRaiseStmt() {
        return has(5);
    }

    public NonlocalStmt nonlocalStmt() {
        return get(6, NonlocalStmt.class);
    }

    public boolean hasNonlocalStmt() {
        return has(6);
    }

    public AssertStmt assertStmt() {
        return get(7, AssertStmt.class);
    }

    public boolean hasAssertStmt() {
        return has(7);
    }

    public ImportName importName() {
        return get(8, ImportName.class);
    }

    public boolean hasImportName() {
        return has(8);
    }

    public ImportFrom importFrom() {
        return get(9, ImportFrom.class);
    }

    public boolean hasImportFrom() {
        return has(9);
    }

    public Assignment assignment() {
        return get(10, Assignment.class);
    }

    public boolean hasAssignment() {
        return has(10);
    }
}
