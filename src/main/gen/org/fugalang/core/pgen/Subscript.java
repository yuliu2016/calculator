package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * subscript: '[' 'slicelist' ']'
 */
public final class Subscript extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("subscript", RuleType.Conjunction);

    public static Subscript of(ParseTreeNode node) {
        return new Subscript(node);
    }

    private Subscript(ParseTreeNode node) {
        super(RULE, node);
    }

    public Slicelist slicelist() {
        return Slicelist.of(getItem(1));
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consumeToken("[");
        r = r && Slicelist.parse(t, lv + 1);
        r = r && t.consumeToken("]");
        t.exit(r);
        return r;
    }
}
