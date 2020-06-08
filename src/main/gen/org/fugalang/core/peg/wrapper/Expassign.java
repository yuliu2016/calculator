package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * expassign:
 * *   | '/' NAME '=' exprlist_star
 */
public final class Expassign extends NodeWrapper {

    public Expassign(ParseTreeNode node) {
        super(node);
    }

    public String name() {
        return get(1, TokenType.NAME);
    }

    public ExprlistStar exprlistStar() {
        return new ExprlistStar(get(3));
    }
}
