package org.fugalang.grammar.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * delimited: STRING '.' item '+'
 */
public final class Delimited extends NodeWrapper {

    public Delimited(ParseTreeNode node) {
        super(node);
    }

    public String string() {
        return get(0, TokenType.STRING);
    }

    public Item item() {
        return new Item(get(2));
    }
}
