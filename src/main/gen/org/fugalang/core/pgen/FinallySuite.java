package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * finally_suite: 'finally' 'suite'
 */
public final class FinallySuite extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("finally_suite", RuleType.Conjunction);

    public static FinallySuite of(ParseTreeNode node) {
        return new FinallySuite(node);
    }

    private FinallySuite(ParseTreeNode node) {
        super(RULE, node);
    }

    public Suite suite() {
        return get(1, Suite::of);
    }
}
