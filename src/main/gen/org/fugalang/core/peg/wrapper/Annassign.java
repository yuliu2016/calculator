package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * annassign:
 * *   | exprlist_star ':' expr ['=' exprlist_star]
 */
public final class Annassign extends NodeWrapper {

    public Annassign(ParseTreeNode node) {
        super(node);
    }

    public ExprlistStar exprlistStar() {
        return new ExprlistStar(get(0));
    }

    public Expr expr() {
        return new Expr(get(2));
    }

    public Annassign4 assignExprlistStar() {
        return new Annassign4(get(3));
    }

    public boolean hasAssignExprlistStar() {
        return has(3);
    }

    /**
     * '=' exprlist_star
     */
    public static final class Annassign4 extends NodeWrapper {

        public Annassign4(ParseTreeNode node) {
            super(node);
        }

        public ExprlistStar exprlistStar() {
            return new ExprlistStar(get(1));
        }
    }
}
