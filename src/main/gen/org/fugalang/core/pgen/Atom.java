package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * atom: 'tuple_atom' | 'list_atom' | 'dict_or_set' | 'NAME' | 'NUMBER' | 'STRING' | 'None' | 'True' | 'False'
 */
public final class Atom extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("atom", RuleType.Disjunction);

    public static Atom of(ParseTreeNode node) {
        return new Atom(node);
    }

    private Atom(ParseTreeNode node) {
        super(RULE, node);
    }

    public TupleAtom tupleAtom() {
        return get(0, TupleAtom::of);
    }

    public boolean hasTupleAtom() {
        return has(0, TupleAtom.RULE);
    }

    public ListAtom listAtom() {
        return get(1, ListAtom::of);
    }

    public boolean hasListAtom() {
        return has(1, ListAtom.RULE);
    }

    public DictOrSet dictOrSet() {
        return get(2, DictOrSet::of);
    }

    public boolean hasDictOrSet() {
        return has(2, DictOrSet.RULE);
    }

    public String name() {
        return get(3, TokenType.NAME);
    }

    public boolean hasName() {
        return has(3, TokenType.NAME);
    }

    public String number() {
        return get(4, TokenType.NUMBER);
    }

    public boolean hasNumber() {
        return has(4, TokenType.NUMBER);
    }

    public String string() {
        return get(5, TokenType.STRING);
    }

    public boolean hasString() {
        return has(5, TokenType.STRING);
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
