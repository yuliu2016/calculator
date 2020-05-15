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
        return Expr.of(get(1));
    }

    public Annassign3 annassign3() {
        return Annassign3.of(get(2));
    }

    public boolean hasAnnassign3() {
        return has(2, Annassign3.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
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
            return ExprlistStar.of(get(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("=");
            r = r && ExprlistStar.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
