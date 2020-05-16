package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * comp_for: 'for' 'targetlist' 'in' 'disjunction' ['comp_iter']
 */
public final class CompFor extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("comp_for", RuleType.Conjunction);

    public static CompFor of(ParseTreeNode node) {
        return new CompFor(node);
    }

    private CompFor(ParseTreeNode node) {
        super(RULE, node);
    }

    public Targetlist targetlist() {
        return get(1, Targetlist::of);
    }

    public Disjunction disjunction() {
        return get(3, Disjunction::of);
    }

    public CompIter compIter() {
        return get(4, CompIter::of);
    }

    public boolean hasCompIter() {
        return has(4, CompIter.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("for");
        r = r && Targetlist.parse(t, lv + 1);
        r = r && t.consume("in");
        r = r && Disjunction.parse(t, lv + 1);
        if (r) CompIter.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
