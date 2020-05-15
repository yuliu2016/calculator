package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * expr: 'conditional' | 'funcdef' | 'disjunction'
 */
public final class Expr extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("expr", RuleType.Disjunction);

    public static Expr of(ParseTreeNode node) {
        return new Expr(node);
    }

    private Expr(ParseTreeNode node) {
        super(RULE, node);
    }

    public Conditional conditional() {
        return Conditional.of(getItem(0));
    }

    public boolean hasConditional() {
        return hasItemOfRule(0, Conditional.RULE);
    }

    public Funcdef funcdef() {
        return Funcdef.of(getItem(1));
    }

    public boolean hasFuncdef() {
        return hasItemOfRule(1, Funcdef.RULE);
    }

    public Disjunction disjunction() {
        return Disjunction.of(getItem(2));
    }

    public boolean hasDisjunction() {
        return hasItemOfRule(2, Disjunction.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Conditional.parse(t, lv + 1);
        r = r || Funcdef.parse(t, lv + 1);
        r = r || Disjunction.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
