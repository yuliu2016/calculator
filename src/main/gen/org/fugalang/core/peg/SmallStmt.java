package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * small_stmt: flow_stmt | del_stmt | nonlocal_stmt | assert_stmt | import_name | import_from | assignment
 */
public final class SmallStmt extends NodeWrapper {

    public SmallStmt(ParseTreeNode node) {
        super(node);
    }

    public FlowStmt flowStmt() {
        return new FlowStmt(get(0));
    }

    public boolean hasFlowStmt() {
        return has(0);
    }

    public DelStmt delStmt() {
        return new DelStmt(get(1));
    }

    public boolean hasDelStmt() {
        return has(1);
    }

    public NonlocalStmt nonlocalStmt() {
        return new NonlocalStmt(get(2));
    }

    public boolean hasNonlocalStmt() {
        return has(2);
    }

    public AssertStmt assertStmt() {
        return new AssertStmt(get(3));
    }

    public boolean hasAssertStmt() {
        return has(3);
    }

    public ImportName importName() {
        return new ImportName(get(4));
    }

    public boolean hasImportName() {
        return has(4);
    }

    public ImportFrom importFrom() {
        return new ImportFrom(get(5));
    }

    public boolean hasImportFrom() {
        return has(5);
    }

    public Assignment assignment() {
        return new Assignment(get(6));
    }

    public boolean hasAssignment() {
        return has(6);
    }
}
