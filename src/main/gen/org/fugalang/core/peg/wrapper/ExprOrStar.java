package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * expr_or_star: star_expr | expr
 */
public final class ExprOrStar extends NodeWrapper {

    public ExprOrStar(ParseTreeNode node) {
        super(node);
    }

    public StarExpr starExpr() {
        return new StarExpr(get(0));
    }

    public boolean hasStarExpr() {
        return has(0);
    }

    public Expr expr() {
        return new Expr(get(1));
    }

    public boolean hasExpr() {
        return has(1);
    }
}
