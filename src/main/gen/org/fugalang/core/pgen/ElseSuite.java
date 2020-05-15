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
        return Suite.of(get(1));
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("else");
        r = r && Suite.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
