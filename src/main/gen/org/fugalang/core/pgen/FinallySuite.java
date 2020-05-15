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
        return Suite.of(getItem(1));
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consumeToken("finally");
        r = r && Suite.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
