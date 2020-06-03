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
        return new Expr(get(1));
    }

    public AssertStmt3 commaExpr() {
        return new AssertStmt3(get(2));
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
            return new Expr(get(1));
        }
    }
}
