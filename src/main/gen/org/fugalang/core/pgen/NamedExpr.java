package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * named_expr: 'NAME' ':=' 'expr' | 'expr'
 */
public final class NamedExpr extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("named_expr", RuleType.Disjunction);

    public static NamedExpr of(ParseTreeNode node) {
        return new NamedExpr(node);
    }

    private NamedExpr(ParseTreeNode node) {
        super(RULE, node);
    }

    public NamedExpr1 namedExpr1() {
        return NamedExpr1.of(get(0));
    }

    public boolean hasNamedExpr1() {
        return has(0, NamedExpr1.RULE);
    }

    public Expr expr() {
        return Expr.of(get(1));
    }

    public boolean hasExpr() {
        return has(1, Expr.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = NamedExpr1.parse(t, lv + 1);
        r = r || Expr.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'NAME' ':=' 'expr'
     */
    public static final class NamedExpr1 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("named_expr:1", RuleType.Conjunction);

        public static NamedExpr1 of(ParseTreeNode node) {
            return new NamedExpr1(node);
        }

        private NamedExpr1(ParseTreeNode node) {
            super(RULE, node);
        }

        public String name() {
            return get(0, TokenType.NAME);
        }

        public Expr expr() {
            return Expr.of(get(2));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume(TokenType.NAME);
            r = r && t.consume(":=");
            r = r && Expr.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
