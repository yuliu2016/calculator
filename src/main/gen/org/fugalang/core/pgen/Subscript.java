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
        return get(1, Slicelist::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("[");
        r = r && Slicelist.parse(t, lv + 1);
        r = r && t.consume("]");
        t.exit(r);
        return r;
    }
}
