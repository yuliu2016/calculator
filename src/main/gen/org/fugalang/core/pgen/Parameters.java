package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * parameters: '(' ['arglist'] ')'
 */
public final class Parameters extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("parameters", RuleType.Conjunction);

    public static Parameters of(ParseTreeNode node) {
        return new Parameters(node);
    }

    private Parameters(ParseTreeNode node) {
        super(RULE, node);
    }

    public Arglist arglist() {
        return get(1, Arglist::of);
    }

    public boolean hasArglist() {
        return has(1, Arglist.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("(");
        if (r) Arglist.parse(t, lv + 1);
        r = r && t.consume(")");
        t.exit(r);
        return r;
    }
}
