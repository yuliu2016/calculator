package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * expr_as_name:
 * *   | expr [as_name]
 */
public final class ExprAsName extends NodeWrapper {

    public ExprAsName(ParseTreeNode node) {
        super(node);
    }

    public Expr expr() {
        return new Expr(get(0));
    }

    public AsName asName() {
        return new AsName(get(1));
    }

    public boolean hasAsName() {
        return has(1);
    }
}
