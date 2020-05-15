package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * comp_iter: 'comp_for' | 'comp_if'
 */
public final class CompIter extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("comp_iter", RuleType.Disjunction);

    public static CompIter of(ParseTreeNode node) {
        return new CompIter(node);
    }

    private CompIter(ParseTreeNode node) {
        super(RULE, node);
    }

    public CompFor compFor() {
        return CompFor.of(get(0));
    }

    public boolean hasCompFor() {
        return has(0, CompFor.RULE);
    }

    public CompIf compIf() {
        return CompIf.of(get(1));
    }

    public boolean hasCompIf() {
        return has(1, CompIf.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = CompFor.parse(t, lv + 1);
        r = r || CompIf.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
