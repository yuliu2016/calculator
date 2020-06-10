package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * raise_stmt:
 * *   | 'raise' expr ['from' expr]
 */
public final class RaiseStmt extends NodeWrapper {

    public RaiseStmt(ParseTreeNode node) {
        super(node);
    }

    public Expr expr() {
        return new Expr(get(1));
    }

    public RaiseStmt3 fromExpr() {
        return new RaiseStmt3(get(2));
    }

    public boolean hasFromExpr() {
        return has(2);
    }

    /**
     * 'from' expr
     */
    public static final class RaiseStmt3 extends NodeWrapper {

        public RaiseStmt3(ParseTreeNode node) {
            super(node);
        }

        public Expr expr() {
            return new Expr(get(1));
        }
    }
}
