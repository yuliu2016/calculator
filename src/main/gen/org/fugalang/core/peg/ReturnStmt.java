package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * return_stmt: 'return' [exprlist_star]
 */
public final class ReturnStmt extends NodeWrapper {

    public ReturnStmt(ParseTreeNode node) {
        super(node);
    }

    public ExprlistStar exprlistStar() {
        return get(1, ExprlistStar.class);
    }

    public boolean hasExprlistStar() {
        return has(1);
    }
}
