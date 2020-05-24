package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * annassign: ':' expr ['=' exprlist_star]
 */
public final class Annassign extends NodeWrapper {

    public Annassign(ParseTreeNode node) {
        super(node);
    }

    public Expr expr() {
        return get(1, Expr.class);
    }

    public Annassign3 assignExprlistStar() {
        return get(2, Annassign3.class);
    }

    public boolean hasAssignExprlistStar() {
        return has(2);
    }

    /**
     * '=' exprlist_star
     */
    public static final class Annassign3 extends NodeWrapper {

        public Annassign3(ParseTreeNode node) {
            super(node);
        }

        public ExprlistStar exprlistStar() {
            return get(1, ExprlistStar.class);
        }
    }
}
