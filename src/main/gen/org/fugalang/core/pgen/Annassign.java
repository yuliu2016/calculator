package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * annassign: ':' 'expr' ['=' 'exprlist_star']
 */
public final class Annassign extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("annassign", RuleType.Conjunction);

    public static Annassign of(ParseTreeNode node) {
        return new Annassign(node);
    }

    private Annassign(ParseTreeNode node) {
        super(RULE, node);
    }

    public Expr expr() {
        return get(1, Expr::of);
    }

    public Annassign3 exprlistStar() {
        return get(2, Annassign3::of);
    }

    public boolean hasExprlistStar() {
        return has(2, Annassign3.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume(":");
        r = r && Expr.parse(t, lv + 1);
        if (r) Annassign3.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '=' 'exprlist_star'
     */
    public static final class Annassign3 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("annassign:3", RuleType.Conjunction);

        public static Annassign3 of(ParseTreeNode node) {
            return new Annassign3(node);
        }

        private Annassign3(ParseTreeNode node) {
            super(RULE, node);
        }

        public ExprlistStar exprlistStar() {
            return get(1, ExprlistStar::of);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (t.recursionGuard(lv)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("=");
            r = r && ExprlistStar.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
