package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * argument: 'NAME' ':=' 'expr' | 'NAME' '=' 'expr' | '**' 'expr' | '*' 'expr' | 'expr'
 */
public final class Argument extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("argument", RuleType.Disjunction);

    public static Argument of(ParseTreeNode node) {
        return new Argument(node);
    }

    private Argument(ParseTreeNode node) {
        super(RULE, node);
    }

    public Argument1 nameExpr() {
        return Argument1.of(get(0));
    }

    public boolean hasNameExpr() {
        return has(0, Argument1.RULE);
    }

    public Argument2 nameExpr1() {
        return Argument2.of(get(1));
    }

    public boolean hasNameExpr1() {
        return has(1, Argument2.RULE);
    }

    public Argument3 expr() {
        return Argument3.of(get(2));
    }

    public boolean hasExpr() {
        return has(2, Argument3.RULE);
    }

    public Argument4 expr1() {
        return Argument4.of(get(3));
    }

    public boolean hasExpr1() {
        return has(3, Argument4.RULE);
    }

    public Expr expr2() {
        return Expr.of(get(4));
    }

    public boolean hasExpr2() {
        return has(4, Expr.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Argument1.parse(t, lv + 1);
        r = r || Argument2.parse(t, lv + 1);
        r = r || Argument3.parse(t, lv + 1);
        r = r || Argument4.parse(t, lv + 1);
        r = r || Expr.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'NAME' ':=' 'expr'
     */
    public static final class Argument1 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("argument:1", RuleType.Conjunction);

        public static Argument1 of(ParseTreeNode node) {
            return new Argument1(node);
        }

        private Argument1(ParseTreeNode node) {
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

    /**
     * 'NAME' '=' 'expr'
     */
    public static final class Argument2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("argument:2", RuleType.Conjunction);

        public static Argument2 of(ParseTreeNode node) {
            return new Argument2(node);
        }

        private Argument2(ParseTreeNode node) {
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
            r = r && t.consume("=");
            r = r && Expr.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }

    /**
     * '**' 'expr'
     */
    public static final class Argument3 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("argument:3", RuleType.Conjunction);

        public static Argument3 of(ParseTreeNode node) {
            return new Argument3(node);
        }

        private Argument3(ParseTreeNode node) {
            super(RULE, node);
        }

        public Expr expr() {
            return Expr.of(get(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("**");
            r = r && Expr.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }

    /**
     * '*' 'expr'
     */
    public static final class Argument4 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("argument:4", RuleType.Conjunction);

        public static Argument4 of(ParseTreeNode node) {
            return new Argument4(node);
        }

        private Argument4(ParseTreeNode node) {
            super(RULE, node);
        }

        public Expr expr() {
            return Expr.of(get(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("*");
            r = r && Expr.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
