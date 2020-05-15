package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * parameters: '(' ['arglist'] ')'
 */
public final class Parameters extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("parameters", RuleType.Conjunction, true);

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

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = t.consumeToken("(");
        if (r) Arglist.parse(t, l + 1);
        r = r && t.consumeToken(")");
        t.exit(r);
        return r;
    }
}
