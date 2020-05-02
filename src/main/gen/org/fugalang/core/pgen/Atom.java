package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * atom: 'compound_atom' | 'simple_atom'
 */
public final class Atom extends DisjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("atom", RuleType.Disjunction, true);

    private final CompoundAtom compoundAtom;
    private final SimpleAtom simpleAtom;

    public Atom(
            CompoundAtom compoundAtom,
            SimpleAtom simpleAtom
    ) {
        this.compoundAtom = compoundAtom;
        this.simpleAtom = simpleAtom;
    }

    @Override
    protected void buildRule() {
        addChoice("compoundAtom", compoundAtom());
        addChoice("simpleAtom", simpleAtom());
    }

    public CompoundAtom compoundAtom() {
        return compoundAtom;
    }

    public boolean hasCompoundAtom() {
        return compoundAtom() != null;
    }

    public SimpleAtom simpleAtom() {
        return simpleAtom;
    }

    public boolean hasSimpleAtom() {
        return simpleAtom() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = CompoundAtom.parse(parseTree, level + 1);
        result = result || SimpleAtom.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
