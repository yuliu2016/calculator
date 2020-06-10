package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * pubassign:
 * *   | '/' NAME '=' exprlist
 */
public final class Pubassign extends NodeWrapper {

    public Pubassign(ParseTreeNode node) {
        super(node);
    }

    public String name() {
        return get(1, TokenType.NAME);
    }

    public Exprlist exprlist() {
        return new Exprlist(get(3));
    }
}
