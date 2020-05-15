package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * named_expr: 'NAME' ':=' 'expr' | 'expr'
 */
public final class NamedExpr extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("named_expr", RuleType.Disjunction, true);

    public static NamedExpr of(ParseTreeNode node) {
        return new NamedExpr(node);
    }

    private NamedExpr(ParseTreeNode node) {
        super(RULE, node);
    }

    public NamedExpr1 namedExpr1() {
        return NamedExpr1.of(getItem(0));
    }

    public boolean hasNamedExpr1() {
        return hasItemOfRule(0, NamedExpr1.RULE);
    }

    public Expr expr() {
        return Expr.of(getItem(1));
    }

    public boolean hasExpr() {
        return hasItemOfRule(1, Expr.RULE);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = NamedExpr1.parse(t, l + 1);
        r = r || Expr.parse(t, l + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'NAME' ':=' 'expr'
     */
    public static final class NamedExpr1 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("named_expr:1", RuleType.Conjunction, false);

        public static NamedExpr1 of(ParseTreeNode node) {
            return new NamedExpr1(node);
        }

        private NamedExpr1(ParseTreeNode node) {
            super(RULE, node);
        }

        public String name() {
            return getItemOfType(0, TokenType.NAME);
        }

        public Expr expr() {
            return Expr.of(getItem(2));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken(TokenType.NAME);
            r = r && t.consumeToken(":=");
            r = r && Expr.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}
