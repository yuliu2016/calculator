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

    @Override
    protected void buildRule() {
        addChoice("compoundAtom", compoundAtom());
        addChoice("simpleAtom", simpleAtom());
    }

    public CompoundAtom compoundAtom() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return CompoundAtom.of(element);
    }

    public boolean hasCompoundAtom() {
        return compoundAtom() != null;
    }

    public SimpleAtom simpleAtom() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return SimpleAtom.of(element);
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
