package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * annassign:
 * *   | target ':' expr ['=' exprlist]
 */
public final class Annassign extends NodeWrapper {

    public Annassign(ParseTreeNode node) {
        super(node);
    }

    public Target target() {
        return new Target(get(0));
    }

    public Expr expr() {
        return new Expr(get(2));
    }

    public Annassign4 assignExprlist() {
        return new Annassign4(get(3));
    }

    public boolean hasAssignExprlist() {
        return has(3);
    }

    /**
     * '=' exprlist
     */
    public static final class Annassign4 extends NodeWrapper {

        public Annassign4(ParseTreeNode node) {
            super(node);
        }

        public Exprlist exprlist() {
            return new Exprlist(get(1));
        }
    }
}
