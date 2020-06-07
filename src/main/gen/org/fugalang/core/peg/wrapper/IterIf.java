package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * iter_if:
 * *   | 'if' named_expr
 */
public final class IterIf extends NodeWrapper {

    public IterIf(ParseTreeNode node) {
        super(node);
    }

    public NamedExpr namedExpr() {
        return new NamedExpr(get(1));
    }
}
