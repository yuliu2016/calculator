package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * else_suite: 'else' 'suite'
 */
public final class ElseSuite extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("else_suite", RuleType.Conjunction, true);

    public static ElseSuite of(ParseTreeNode node) {
        return new ElseSuite(node);
    }

    private ElseSuite(ParseTreeNode node) {
        super(RULE, node);
    }

    public Suite suite() {
        return Suite.of(getItem(1));
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = t.consumeToken("else");
        r = r && Suite.parse(t, l + 1);
        t.exit(r);
        return r;
    }
}
