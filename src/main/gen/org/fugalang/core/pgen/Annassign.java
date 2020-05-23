package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.FugaRules;

/**
 * annassign: ':' 'expr' ['=' 'exprlist_star']
 */
public final class Annassign extends NodeWrapper {

    public Annassign(ParseTreeNode node) {
        super(FugaRules.ANNASSIGN, node);
    }

    public Expr expr() {
        return get(1, Expr.class);
    }

    public Annassign3 exprlistStar() {
        return get(2, Annassign3.class);
    }

    public boolean hasExprlistStar() {
        return has(2);
    }

    /**
     * '=' 'exprlist_star'
     */
    public static final class Annassign3 extends NodeWrapper {

        public Annassign3(ParseTreeNode node) {
            super(FugaRules.ANNASSIGN_3, node);
        }

        public ExprlistStar exprlistStar() {
            return get(1, ExprlistStar.class);
        }
    }
}
