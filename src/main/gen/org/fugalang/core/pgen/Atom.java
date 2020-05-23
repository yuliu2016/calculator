package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * atom: tuple_atom | list_atom | dict_or_set | NAME | NUMBER | STRING | 'None' | 'True' | 'False'
 */
public final class Atom extends NodeWrapper {

    public Atom(ParseTreeNode node) {
        super(node);
    }

    public TupleAtom tupleAtom() {
        return get(0, TupleAtom.class);
    }

    public boolean hasTupleAtom() {
        return has(0);
    }

    public ListAtom listAtom() {
        return get(1, ListAtom.class);
    }

    public boolean hasListAtom() {
        return has(1);
    }

    public DictOrSet dictOrSet() {
        return get(2, DictOrSet.class);
    }

    public boolean hasDictOrSet() {
        return has(2);
    }

    public String name() {
        return get(3, TokenType.NAME);
    }

    public boolean hasName() {
        return has(3);
    }

    public String number() {
        return get(4, TokenType.NUMBER);
    }

    public boolean hasNumber() {
        return has(4);
    }

    public String string() {
        return get(5, TokenType.STRING);
    }

    public boolean hasString() {
        return has(5);
    }

    public boolean isNone() {
        return is(6);
    }

    public boolean isTrue() {
        return is(7);
    }

    public boolean isFalse() {
        return is(8);
    }
}
