package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * while_stmt:
 * *   | 'while' named_expr suite [else_suite]
 */
public final class WhileStmt extends NodeWrapper {

    public WhileStmt(ParseTreeNode node) {
        super(node);
    }

    public NamedExpr namedExpr() {
        return new NamedExpr(get(1));
    }

    public Suite suite() {
        return new Suite(get(2));
    }

    public ElseSuite elseSuite() {
        return new ElseSuite(get(3));
    }

    public boolean hasElseSuite() {
        return has(3);
    }
}
