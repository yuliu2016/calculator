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

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = CompoundAtom.parse(parseTree, level + 1);
        result = result || SimpleAtom.parse(parseTree, level + 1);

        parseTree.exit(result);
        return result;
    }
}
