package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * comp_iter: 'comp_for' | 'comp_if'
 */
public final class CompIter extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("comp_iter", RuleType.Disjunction, true);

    public static CompIter of(ParseTreeNode node) {
        return new CompIter(node);
    }

    private CompIter(ParseTreeNode node) {
        super(RULE, node);
    }

    public CompFor compFor() {
        return CompFor.of(getItem(0));
    }

    public boolean hasCompFor() {
        return hasItemOfRule(0, CompFor.RULE);
    }

    public CompIf compIf() {
        return CompIf.of(getItem(1));
    }

    public boolean hasCompIf() {
        return hasItemOfRule(1, CompIf.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = CompFor.parse(parseTree, level + 1);
        result = result || CompIf.parse(parseTree, level + 1);

        parseTree.exit(result);
        return result;
    }
}
