package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * comp_for: 'for' 'targetlist' 'in' 'disjunction' ['comp_iter']
 */
public final class CompFor extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("comp_for", RuleType.Conjunction, true);

    public static CompFor of(ParseTreeNode node) {
        return new CompFor(node);
    }

    private CompFor(ParseTreeNode node) {
        super(RULE, node);
    }

    public boolean isTokenFor() {
        return true;
    }

    public Targetlist targetlist() {
        return Targetlist.of(getItem(1));
    }

    public boolean isTokenIn() {
        return true;
    }

    public Disjunction disjunction() {
        return Disjunction.of(getItem(3));
    }

    public CompIter compIter() {
        return CompIter.of(getItem(4));
    }

    public boolean hasCompIter() {
        return hasItemOfRule(4, CompIter.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("for");
        result = result && Targetlist.parse(parseTree, level + 1);
        result = result && parseTree.consumeToken("in");
        result = result && Disjunction.parse(parseTree, level + 1);
        if (result) CompIter.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
