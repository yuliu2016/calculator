package org.fugalang.core.pgen;

import org.fugalang.core.parser.DisjunctionRule;

// small_stmt: 'expr_stmt' | 'del_stmt' | 'pass_stmt' | 'flow_stmt' | 'import_stmt' | 'assert_stmt'
public final class SmallStmt extends DisjunctionRule {
    private final ExprStmt exprStmt;
    private final DelStmt delStmt;
    private final PassStmt passStmt;
    private final FlowStmt flowStmt;
    private final ImportStmt importStmt;
    private final AssertStmt assertStmt;

    public SmallStmt(
            ExprStmt exprStmt,
            DelStmt delStmt,
            PassStmt passStmt,
            FlowStmt flowStmt,
            ImportStmt importStmt,
            AssertStmt assertStmt
    ) {
        this.exprStmt = exprStmt;
        this.delStmt = delStmt;
        this.passStmt = passStmt;
        this.flowStmt = flowStmt;
        this.importStmt = importStmt;
        this.assertStmt = assertStmt;

        addChoice("exprStmt", exprStmt);
        addChoice("delStmt", delStmt);
        addChoice("passStmt", passStmt);
        addChoice("flowStmt", flowStmt);
        addChoice("importStmt", importStmt);
        addChoice("assertStmt", assertStmt);
    }

    public ExprStmt getExprStmt() {
        return exprStmt;
    }

    public DelStmt getDelStmt() {
        return delStmt;
    }

    public PassStmt getPassStmt() {
        return passStmt;
    }

    public FlowStmt getFlowStmt() {
        return flowStmt;
    }

    public ImportStmt getImportStmt() {
        return importStmt;
    }

    public AssertStmt getAssertStmt() {
        return assertStmt;
    }
}
