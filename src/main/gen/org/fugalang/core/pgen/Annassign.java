package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * annassign: ':' 'expr' ['=' 'exprlist_star']
 */
public final class Annassign extends NodeWrapper {

    public Annassign(ParseTreeNode node) {
        super(ParserRules.ANNASSIGN, node);
    }

    public Expr expr() {
        return get(1, Expr::new);
    }

    public Annassign3 exprlistStar() {
        return get(2, Annassign3::new);
    }

    public boolean hasExprlistStar() {
        return has(2, ParserRules.ANNASSIGN_3);
    }

    /**
     * '=' 'exprlist_star'
     */
    public static final class Annassign3 extends NodeWrapper {

        public Annassign3(ParseTreeNode node) {
            super(ParserRules.ANNASSIGN_3, node);
        }

        public ExprlistStar exprlistStar() {
            return get(1, ExprlistStar::new);
        }
    }
}
