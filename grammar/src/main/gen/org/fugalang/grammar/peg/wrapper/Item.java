package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * item:
 * *   | group
 * *   | optional
 * *   | NAME
 * *   | STRING
 */
public final class Item extends NodeWrapper {

    public Item(ParseTreeNode node) {
        super(node);
    }

    public Group group() {
        return new Group(get(0));
    }

    public boolean hasGroup() {
        return has(0);
    }

    public Optional optional() {
        return new Optional(get(1));
    }

    public boolean hasOptional() {
        return has(1);
    }

    public String name() {
        return get(2, TokenType.NAME);
    }

    public boolean hasName() {
        return has(2);
    }

    public String string() {
        return get(3, TokenType.STRING);
    }

    public boolean hasString() {
        return has(3);
    }
}
