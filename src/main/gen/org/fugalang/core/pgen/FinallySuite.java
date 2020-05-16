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

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("finally");
        r = r && Suite.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
