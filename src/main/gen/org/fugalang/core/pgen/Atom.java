package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * atom: 'compound_atom' | 'simple_atom'
 */
public final class Atom extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("atom", RuleType.Disjunction, true);

    public static Atom of(ParseTreeNode node) {
        return new Atom(node);
    }

    private Atom(ParseTreeNode node) {
        super(RULE, node);
    }

    public CompoundAtom compoundAtom() {
        return CompoundAtom.of(getItem(0));
    }

    public boolean hasCompoundAtom() {
        return hasItemOfRule(0, CompoundAtom.RULE);
    }

    public SimpleAtom simpleAtom() {
        return SimpleAtom.of(getItem(1));
    }

    public boolean hasSimpleAtom() {
        return hasItemOfRule(1, SimpleAtom.RULE);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = CompoundAtom.parse(t, l + 1);
        r = r || SimpleAtom.parse(t, l + 1);
        t.exit(r);
        return r;
    }
}
