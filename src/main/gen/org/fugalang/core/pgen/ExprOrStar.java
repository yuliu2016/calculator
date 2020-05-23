package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * expr_or_star: 'star_expr' | 'expr'
 */
public final class ExprOrStar extends NodeWrapper {

    public ExprOrStar(ParseTreeNode node) {
        super(ParserRules.EXPR_OR_STAR, node);
    }

    public StarExpr starExpr() {
        return get(0, StarExpr.class);
    }

    public boolean hasStarExpr() {
        return has(0);
    }

    public Expr expr() {
        return get(1, Expr.class);
    }

    public boolean hasExpr() {
        return has(1);
    }
}
