package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * return_stmt:
 * *   | 'return' [exprlist_star]
 */
public final class ReturnStmt extends NodeWrapper {

    public ReturnStmt(ParseTreeNode node) {
        super(node);
    }

    public ExprlistStar exprlistStar() {
        return new ExprlistStar(get(1));
    }

    public boolean hasExprlistStar() {
        return has(1);
    }
}
