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
        return new RaiseStmt2(get(1));
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
            return new Expr(get(0));
        }

        public RaiseStmt22 fromExpr() {
            return new RaiseStmt22(get(1));
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
            return new Expr(get(1));
        }
    }
}
