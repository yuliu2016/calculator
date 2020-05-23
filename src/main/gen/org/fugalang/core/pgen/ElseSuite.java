package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * else_suite: 'else' 'suite'
 */
public final class ElseSuite extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("else_suite", RuleType.Conjunction);

    public static ElseSuite of(ParseTreeNode node) {
        return new ElseSuite(node);
    }

    private ElseSuite(ParseTreeNode node) {
        super(RULE, node);
    }

    public Suite suite() {
        return get(1, Suite::of);
    }
}
