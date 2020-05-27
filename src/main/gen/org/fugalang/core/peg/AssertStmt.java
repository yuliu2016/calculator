package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * assert_stmt: 'assert' expr [',' expr]
 */
public final class AssertStmt extends NodeWrapper {

    public AssertStmt(ParseTreeNode node) {
        super(node);
    }

    public Expr expr() {
        return get(1, Expr.class);
    }

    public AssertStmt3 commaExpr() {
        return get(2, AssertStmt3.class);
    }

    public boolean hasCommaExpr() {
        return has(2);
    }

    /**
     * ',' expr
     */
    public static final class AssertStmt3 extends NodeWrapper {

        public AssertStmt3(ParseTreeNode node) {
            super(node);
        }

        public Expr expr() {
            return get(1, Expr.class);
        }
    }
}