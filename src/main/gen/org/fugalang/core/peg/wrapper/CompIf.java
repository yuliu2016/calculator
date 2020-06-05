package org.fugalang.core.peg.wrapper;

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
        return new NamedExpr(get(1));
    }

    public CompIter compIter() {
        return new CompIter(get(2));
    }

    public boolean hasCompIter() {
        return has(2);
    }
}
