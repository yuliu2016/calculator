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
        return Targetlist.of(getItem(1));
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

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consumeToken("for");
        r = r && Targetlist.parse(t, lv + 1);
        r = r && t.consumeToken("in");
        r = r && Disjunction.parse(t, lv + 1);
        if (r) CompIter.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
