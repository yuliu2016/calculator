package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * comp_if: 'if' 'named_expr' ['comp_iter']
 */
public final class CompIf extends NodeWrapper {

    public CompIf(ParseTreeNode node) {
        super(ParserRules.COMP_IF, node);
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
