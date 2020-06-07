package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * elif_stmt:
 * *   | 'elif' named_expr suite
 */
public final class ElifStmt extends NodeWrapper {

    public ElifStmt(ParseTreeNode node) {
        super(node);
    }

    public NamedExpr namedExpr() {
        return new NamedExpr(get(1));
    }

    public Suite suite() {
        return new Suite(get(2));
    }
}
