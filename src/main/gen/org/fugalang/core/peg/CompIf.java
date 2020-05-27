package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * comp_if: 'if' named_expr [comp_iter]
 */
public final class CompIf extends NodeWrapper {

    public CompIf(ParseTreeNode node) {
        super(node);
    }

    public NamedExpr namedExpr() {
        return get(1, NamedExpr.class);
    }

    public CompIter compIter() {
        return get(2, CompIter.class);
    }

    public boolean hasCompIter() {
        return has(2);
    }
}
