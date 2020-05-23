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
}
