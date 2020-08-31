package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * set_items:
 * *   | exprlist_star
 */
public final class SetItems extends NodeWrapper {

    public SetItems(ParseTreeNode node) {
        super(node);
    }

    public ExprlistStar exprlistStar() {
        return new ExprlistStar(get(0));
    }
}
