package org.fugalang.core.pgen;

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
        return get(0, FlowStmt.class);
    }

    public boolean hasFlowStmt() {
        return has(0);
    }

    public DelStmt delStmt() {
        return get(1, DelStmt.class);
    }

    public boolean hasDelStmt() {
        return has(1);
    }

    public NonlocalStmt nonlocalStmt() {
        return get(2, NonlocalStmt.class);
    }

    public boolean hasNonlocalStmt() {
        return has(2);
    }

    public AssertStmt assertStmt() {
        return get(3, AssertStmt.class);
    }

    public boolean hasAssertStmt() {
        return has(3);
    }

    public ImportName importName() {
        return get(4, ImportName.class);
    }

    public boolean hasImportName() {
        return has(4);
    }

    public ImportFrom importFrom() {
        return get(5, ImportFrom.class);
    }

    public boolean hasImportFrom() {
        return has(5);
    }

    public Assignment assignment() {
        return get(6, Assignment.class);
    }

    public boolean hasAssignment() {
        return has(6);
    }
}
