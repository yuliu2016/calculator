package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * subscript: '[' 'slicelist' ']'
 */
public final class Subscript extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("subscript", RuleType.Conjunction, true);

    public static Subscript of(ParseTreeNode node) {
        return new Subscript(node);
    }

    private Subscript(ParseTreeNode node) {
        super(RULE, node);
    }

    public Slicelist slicelist() {
        return Slicelist.of(getItem(1));
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = t.consumeToken("[");
        r = r && Slicelist.parse(t, l + 1);
        r = r && t.consumeToken("]");
        t.exit(r);
        return r;
    }
}
