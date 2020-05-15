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
        return Disjunction.of(get(1));
    }

    public Disjunction disjunction1() {
        return Disjunction.of(get(3));
    }

    public Expr expr() {
        return Expr.of(get(5));
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
