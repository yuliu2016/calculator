package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * except_clause:
 * *   | 'except' [expr_as_name] suite
 */
public final class ExceptClause extends NodeWrapper {

    public ExceptClause(ParseTreeNode node) {
        super(node);
    }

    public ExprAsName exprAsName() {
        return new ExprAsName(get(1));
    }

    public boolean hasExprAsName() {
        return has(1);
    }

    public Suite suite() {
        return new Suite(get(2));
    }
}
