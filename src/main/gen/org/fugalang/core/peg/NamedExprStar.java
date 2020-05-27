package org.fugalang.core.peg;

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
        return get(0, StarExpr.class);
    }

    public boolean hasStarExpr() {
        return has(0);
    }

    public NamedExpr namedExpr() {
        return get(1, NamedExpr.class);
    }

    public boolean hasNamedExpr() {
        return has(1);
    }
}