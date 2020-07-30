package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * atom:
 * *   | tuple_atom
 * *   | list_iter
 * *   | list_atom
 * *   | set_atom
 * *   | dict_iter
 * *   | dict_atom
 * *   | builder
 * *   | NAME
 * *   | NUMBER
 * *   | STRING
 * *   | 'None'
 * *   | 'True'
 * *   | 'False'
 */
public final class Atom extends NodeWrapper {

    public Atom(ParseTreeNode node) {
        super(node);
    }

    public TupleAtom tupleAtom() {
        return new TupleAtom(get(0));
    }

    public boolean hasTupleAtom() {
        return has(0);
    }

    public ListIter listIter() {
        return new ListIter(get(1));
    }

    public boolean hasListIter() {
        return has(1);
    }

    public ListAtom listAtom() {
        return new ListAtom(get(2));
    }

    public boolean hasListAtom() {
        return has(2);
    }

    public SetAtom setAtom() {
        return new SetAtom(get(3));
    }

    public boolean hasSetAtom() {
        return has(3);
    }

    public DictIter dictIter() {
        return new DictIter(get(4));
    }

    public boolean hasDictIter() {
        return has(4);
    }

    public DictAtom dictAtom() {
        return new DictAtom(get(5));
    }

    public boolean hasDictAtom() {
        return has(5);
    }

    public Builder builder() {
        return new Builder(get(6));
    }

    public boolean hasBuilder() {
        return has(6);
    }

    public String name() {
        return get(7, TokenType.NAME);
    }

    public boolean hasName() {
        return has(7);
    }

    public String number() {
        return get(8, TokenType.NUMBER);
    }

    public boolean hasNumber() {
        return has(8);
    }

    public String string() {
        return get(9, TokenType.STRING);
    }

    public boolean hasString() {
        return has(9);
    }

    public boolean isNone() {
        return is(10);
    }

    public boolean isTrue() {
        return is(11);
    }

    public boolean isFalse() {
        return is(12);
    }
}
