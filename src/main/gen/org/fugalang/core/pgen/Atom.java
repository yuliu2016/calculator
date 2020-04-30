package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.DisjunctionRule;

// atom: 'compound_atom' | 'simple_atom'
public final class Atom extends DisjunctionRule {
    public static final String RULE_NAME = "atom";

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
        setExplicitName(RULE_NAME);
        addChoice("compoundAtom", compoundAtom);
        addChoice("simpleAtom", simpleAtom);
    }

    public CompoundAtom compoundAtom() {
        return compoundAtom;
    }

    public SimpleAtom simpleAtom() {
        return simpleAtom;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        var result = false;
        parseTree.exit(level, marker, result);
        return result;
    }
}
