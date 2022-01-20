package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * argument:
 * *   | STRING
 */
public final class Argument extends NodeWrapper {

    public Argument(ParseTreeNode node) {
        super(node);
    }

    public String string() {
        return get(0, TokenType.STRING);
    }
}
