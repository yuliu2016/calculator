package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * conditional: 'if' 'disjunction' '?' 'disjunction' 'else' 'expr'
 */
public final class Conditional extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("conditional", RuleType.Conjunction);

    public static Conditional of(ParseTreeNode node) {
        return new Conditional(node);
    }

    private Conditional(ParseTreeNode node) {
        super(RULE, node);
    }

    public Disjunction disjunction() {
        return get(1, Disjunction::of);
    }

    public Disjunction disjunction1() {
        return get(3, Disjunction::of);
    }

    public Expr expr() {
        return get(5, Expr::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("if");
        r = r && Disjunction.parse(t, lv + 1);
        r = r && t.consume("?");
        r = r && Disjunction.parse(t, lv + 1);
        r = r && t.consume("else");
        r = r && Expr.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
