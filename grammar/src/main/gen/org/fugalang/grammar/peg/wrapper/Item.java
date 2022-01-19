package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * item:
 * *   | group
 * *   | optional
 * *   | custom_match
 * *   | small_optional
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

    public CustomMatch customMatch() {
        return new CustomMatch(get(2));
    }

    public boolean hasCustomMatch() {
        return has(2);
    }

    public SmallOptional smallOptional() {
        return new SmallOptional(get(3));
    }

    public boolean hasSmallOptional() {
        return has(3);
    }

    public String name() {
        return get(4, TokenType.NAME);
    }

    public boolean hasName() {
        return has(4);
    }

    public String string() {
        return get(5, TokenType.STRING);
    }

    public boolean hasString() {
        return has(5);
    }
}
