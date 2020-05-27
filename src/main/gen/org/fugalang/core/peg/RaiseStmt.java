package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * raise_stmt: 'raise' [expr ['from' expr]]
 */
public final class RaiseStmt extends NodeWrapper {

    public RaiseStmt(ParseTreeNode node) {
        super(node);
    }

    public RaiseStmt2 raiseStmt2() {
        return get(1, RaiseStmt2.class);
    }

    public boolean hasRaiseStmt2() {
        return has(1);
    }

    /**
     * expr ['from' expr]
     */
    public static final class RaiseStmt2 extends NodeWrapper {

        public RaiseStmt2(ParseTreeNode node) {
            super(node);
        }

        public Expr expr() {
            return get(0, Expr.class);
        }

        public RaiseStmt22 fromExpr() {
            return get(1, RaiseStmt22.class);
        }

        public boolean hasFromExpr() {
            return has(1);
        }
    }

    /**
     * 'from' expr
     */
    public static final class RaiseStmt22 extends NodeWrapper {

        public RaiseStmt22(ParseTreeNode node) {
            super(node);
        }

        public Expr expr() {
            return get(1, Expr.class);
        }
    }
}
