package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * expr: 'if' 'disjunction' '?' 'disjunction' 'else' 'expr' | 'funcdef' | 'disjunction'
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

    public Expr1 expr1() {
        return Expr1.of(getItem(0));
    }

    public boolean hasExpr1() {
        return hasItemOfRule(0, Expr1.RULE);
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
        r = Expr1.parse(t, lv + 1);
        r = r || Funcdef.parse(t, lv + 1);
        r = r || Disjunction.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'if' 'disjunction' '?' 'disjunction' 'else' 'expr'
     */
    public static final class Expr1 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("expr:1", RuleType.Conjunction);

        public static Expr1 of(ParseTreeNode node) {
            return new Expr1(node);
        }

        private Expr1(ParseTreeNode node) {
            super(RULE, node);
        }

        public Disjunction disjunction() {
            return Disjunction.of(getItem(1));
        }

        public Disjunction disjunction1() {
            return Disjunction.of(getItem(3));
        }

        public Expr expr() {
            return Expr.of(getItem(5));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consumeToken("if");
            r = r && Disjunction.parse(t, lv + 1);
            r = r && t.consumeToken("?");
            r = r && Disjunction.parse(t, lv + 1);
            r = r && t.consumeToken("else");
            r = r && Expr.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
