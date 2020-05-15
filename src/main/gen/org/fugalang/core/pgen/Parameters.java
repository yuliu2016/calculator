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
        return Arglist.of(getItem(1));
    }

    public boolean hasArglist() {
        return hasItemOfRule(1, Arglist.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consumeToken("(");
        if (r) Arglist.parse(t, lv + 1);
        r = r && t.consumeToken(")");
        t.exit(r);
        return r;
    }
}
