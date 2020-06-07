package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * atom: collection | NAME | NUMBER | STRING | 'None' | 'True' | 'False'
 */
public final class Atom extends NodeWrapper {

    public Atom(ParseTreeNode node) {
        super(node);
    }

    public Collection collection() {
        return new Collection(get(0));
    }

    public boolean hasCollection() {
        return has(0);
    }

    public String name() {
        return get(1, TokenType.NAME);
    }

    public boolean hasName() {
        return has(1);
    }

    public String number() {
        return get(2, TokenType.NUMBER);
    }

    public boolean hasNumber() {
        return has(2);
    }

    public String string() {
        return get(3, TokenType.STRING);
    }

    public boolean hasString() {
        return has(3);
    }

    public boolean isNone() {
        return is(4);
    }

    public boolean isTrue() {
        return is(5);
    }

    public boolean isFalse() {
        return is(6);
    }
}
