package org.fugalang.core.pgen;

import org.fugalang.core.parser.DisjunctionRule;
import org.fugalang.core.parser.ParseTree;

/**
 * small_stmt: 'expr_stmt' | 'del_stmt' | 'pass_stmt' | 'flow_stmt' | 'import_stmt' | 'assert_stmt'
 */
public final class SmallStmt extends DisjunctionRule {
    public static final String RULE_NAME = "small_stmt";

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
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addChoice("exprStmt", exprStmt);
        addChoice("delStmt", delStmt);
        addChoice("passStmt", passStmt);
        addChoice("flowStmt", flowStmt);
        addChoice("importStmt", importStmt);
        addChoice("assertStmt", assertStmt);
    }

    public ExprStmt exprStmt() {
        return exprStmt;
    }

    public boolean hasExprStmt() {
        return exprStmt() != null;
    }

    public DelStmt delStmt() {
        return delStmt;
    }

    public boolean hasDelStmt() {
        return delStmt() != null;
    }

    public PassStmt passStmt() {
        return passStmt;
    }

    public boolean hasPassStmt() {
        return passStmt() != null;
    }

    public FlowStmt flowStmt() {
        return flowStmt;
    }

    public boolean hasFlowStmt() {
        return flowStmt() != null;
    }

    public ImportStmt importStmt() {
        return importStmt;
    }

    public boolean hasImportStmt() {
        return importStmt() != null;
    }

    public AssertStmt assertStmt() {
        return assertStmt;
    }

    public boolean hasAssertStmt() {
        return assertStmt() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
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
