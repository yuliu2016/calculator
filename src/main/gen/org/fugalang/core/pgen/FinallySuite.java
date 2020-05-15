package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * finally_suite: 'finally' 'suite'
 */
public final class FinallySuite extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("finally_suite", RuleType.Conjunction, true);

    public static FinallySuite of(ParseTreeNode node) {
        return new FinallySuite(node);
    }

    private FinallySuite(ParseTreeNode node) {
        super(RULE, node);
    }

    public Suite suite() {
        return Suite.of(getItem(1));
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = t.consumeToken("finally");
        r = r && Suite.parse(t, l + 1);
        t.exit(r);
        return r;
    }
}
