package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * with_item: 'expr' ['as' 'NAME']
 */
public final class WithItem extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("with_item", RuleType.Conjunction);

    public static WithItem of(ParseTreeNode node) {
        return new WithItem(node);
    }

    private WithItem(ParseTreeNode node) {
        super(RULE, node);
    }

    public Expr expr() {
        return Expr.of(get(0));
    }

    public WithItem2 asName() {
        return WithItem2.of(get(1));
    }

    public boolean hasAsName() {
        return has(1, WithItem2.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Expr.parse(t, lv + 1);
        if (r) WithItem2.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'as' 'NAME'
     */
    public static final class WithItem2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("with_item:2", RuleType.Conjunction);

        public static WithItem2 of(ParseTreeNode node) {
            return new WithItem2(node);
        }

        private WithItem2(ParseTreeNode node) {
            super(RULE, node);
        }

        public String name() {
            return get(1, TokenType.NAME);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("as");
            r = r && t.consume(TokenType.NAME);
            t.exit(r);
            return r;
        }
    }
}
