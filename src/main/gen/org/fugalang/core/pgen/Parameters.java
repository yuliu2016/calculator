package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * parameters: '(' ['arglist'] ')'
 */
public final class Parameters extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("parameters", RuleType.Conjunction);

    public static Parameters of(ParseTreeNode node) {
        return new Parameters(node);
    }

    private Parameters(ParseTreeNode node) {
        super(RULE, node);
    }

    public Arglist arglist() {
        return get(1, Arglist::of);
    }

    public boolean hasArglist() {
        return has(1, Arglist.RULE);
    }
}
