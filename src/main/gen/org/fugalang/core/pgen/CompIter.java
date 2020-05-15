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

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = CompFor.parse(t, l + 1);
        r = r || CompIf.parse(t, l + 1);
        t.exit(r);
        return r;
    }
}
