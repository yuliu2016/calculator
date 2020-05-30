package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * expr_as_name: expr [as_name]
 */
public final class ExprAsName extends NodeWrapper {

    public ExprAsName(ParseTreeNode node) {
        super(node);
    }

    public Expr expr() {
        return get(0, Expr.class);
    }

    public AsName asName() {
        return get(1, AsName.class);
    }

    public boolean hasAsName() {
        return has(1);
    }
}
