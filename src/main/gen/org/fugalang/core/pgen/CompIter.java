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
        return get(0, CompFor::of);
    }

    public boolean hasCompFor() {
        return has(0, CompFor.RULE);
    }

    public CompIf compIf() {
        return get(1, CompIf::of);
    }

    public boolean hasCompIf() {
        return has(1, CompIf.RULE);
    }
}
