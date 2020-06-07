package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * set_atom: '{' [exprlist_star] '}'
 */
public final class SetAtom extends NodeWrapper {

    public SetAtom(ParseTreeNode node) {
        super(node);
    }

    public ExprlistStar exprlistStar() {
        return new ExprlistStar(get(1));
    }

    public boolean hasExprlistStar() {
        return has(1);
    }
}
