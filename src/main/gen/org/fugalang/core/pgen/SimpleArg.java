package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * simple_arg: 'NAME' ['=' 'expr']
 */
public final class SimpleArg extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("simple_arg", RuleType.Conjunction, true);

    public static SimpleArg of(ParseTreeNode node) {
        return new SimpleArg(node);
    }

    private SimpleArg(ParseTreeNode node) {
        super(RULE, node);
    }

    public String name() {
        return getItemOfType(0, TokenType.NAME);
    }

    public SimpleArg2 simpleArg2() {
        return SimpleArg2.of(getItem(1));
    }

    public boolean hasSimpleArg2() {
        return hasItemOfRule(1, SimpleArg2.RULE);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = t.consumeToken(TokenType.NAME);
        if (r) SimpleArg2.parse(t, l + 1);
        t.exit(r);
        return r;
    }

    /**
     * '=' 'expr'
     */
    public static final class SimpleArg2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("simple_arg:2", RuleType.Conjunction, false);

        public static SimpleArg2 of(ParseTreeNode node) {
            return new SimpleArg2(node);
        }

        private SimpleArg2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Expr expr() {
            return Expr.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("=");
            r = r && Expr.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}
