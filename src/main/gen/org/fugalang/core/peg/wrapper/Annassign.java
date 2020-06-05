package org.fugalang.core.peg.wrapper;

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
        return new Expr(get(1));
    }

    public Annassign3 assignExprlistStar() {
        return new Annassign3(get(2));
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
            return new ExprlistStar(get(1));
        }
    }
}
