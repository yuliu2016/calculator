package org.fugalang.core.pgen;

// small_stmt: 'expr_stmt' | 'del_stmt' | 'pass_stmt' | 'flow_stmt' | 'import_stmt' | 'assert_stmt'
public class SmallStmt {
    public final ExprStmt exprStmt;
    public final DelStmt delStmt;
    public final PassStmt passStmt;
    public final FlowStmt flowStmt;
    public final ImportStmt importStmt;
    public final AssertStmt assertStmt;

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
    }
}
