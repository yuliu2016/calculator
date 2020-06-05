package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * named_expr_star: star_expr | named_expr
 */
public final class NamedExprStar extends NodeWrapper {

    public NamedExprStar(ParseTreeNode node) {
        super(node);
    }

    public StarExpr starExpr() {
        return new StarExpr(get(0));
    }

    public boolean hasStarExpr() {
        return has(0);
    }

    public NamedExpr namedExpr() {
        return new NamedExpr(get(1));
    }

    public boolean hasNamedExpr() {
        return has(1);
    }
}
