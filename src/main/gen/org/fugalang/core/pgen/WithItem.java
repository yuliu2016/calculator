package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * with_item: 'expr' ['as' 'NAME']
 */
public final class WithItem extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("with_item", RuleType.Conjunction, true);

    public static WithItem of(ParseTreeNode node) {
        return new WithItem(node);
    }

    private WithItem(ParseTreeNode node) {
        super(RULE, node);
    }

    public Expr expr() {
        return Expr.of(getItem(0));
    }

    public WithItem2 withItem2() {
        return WithItem2.of(getItem(1));
    }

    public boolean hasWithItem2() {
        return hasItemOfRule(1, WithItem2.RULE);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = Expr.parse(t, l + 1);
        if (r) WithItem2.parse(t, l + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'as' 'NAME'
     */
    public static final class WithItem2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("with_item:2", RuleType.Conjunction, false);

        public static WithItem2 of(ParseTreeNode node) {
            return new WithItem2(node);
        }

        private WithItem2(ParseTreeNode node) {
            super(RULE, node);
        }

        public String name() {
            return getItemOfType(1, TokenType.NAME);
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("as");
            r = r && t.consumeToken(TokenType.NAME);
            t.exit(r);
            return r;
        }
    }
}
