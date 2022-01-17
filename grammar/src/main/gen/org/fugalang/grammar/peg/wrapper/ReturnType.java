package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * return_type:
 * *   | '[' NAME ['*'] ']'
 */
public final class ReturnType extends NodeWrapper {

    public ReturnType(ParseTreeNode node) {
        super(node);
    }

    public String name() {
        return get(1, TokenType.NAME);
    }

    public boolean isTimes() {
        return is(2);
    }
}
