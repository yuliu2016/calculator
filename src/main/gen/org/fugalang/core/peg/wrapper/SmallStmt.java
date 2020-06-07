package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * small_stmt:
 * *   | 'pass'
 * *   | 'break'
 * *   | 'continue'
 * *   | return_stmt
 * *   | raise_stmt
 * *   | del_stmt
 * *   | nonlocal_stmt
 * *   | assert_stmt
 * *   | import_name
 * *   | import_from
 * *   | assignment
 */
public final class SmallStmt extends NodeWrapper {

    public SmallStmt(ParseTreeNode node) {
        super(node);
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

    public ReturnStmt returnStmt() {
        return new ReturnStmt(get(3));
    }

    public boolean hasReturnStmt() {
        return has(3);
    }

    public RaiseStmt raiseStmt() {
        return new RaiseStmt(get(4));
    }

    public boolean hasRaiseStmt() {
        return has(4);
    }

    public DelStmt delStmt() {
        return new DelStmt(get(5));
    }

    public boolean hasDelStmt() {
        return has(5);
    }

    public NonlocalStmt nonlocalStmt() {
        return new NonlocalStmt(get(6));
    }

    public boolean hasNonlocalStmt() {
        return has(6);
    }

    public AssertStmt assertStmt() {
        return new AssertStmt(get(7));
    }

    public boolean hasAssertStmt() {
        return has(7);
    }

    public ImportName importName() {
        return new ImportName(get(8));
    }

    public boolean hasImportName() {
        return has(8);
    }

    public ImportFrom importFrom() {
        return new ImportFrom(get(9));
    }

    public boolean hasImportFrom() {
        return has(9);
    }

    public Assignment assignment() {
        return new Assignment(get(10));
    }

    public boolean hasAssignment() {
        return has(10);
    }
}
