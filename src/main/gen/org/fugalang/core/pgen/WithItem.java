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
        return get(0, Expr::of);
    }

    public WithItem2 asName() {
        return get(1, WithItem2::of);
    }

    public boolean hasAsName() {
        return has(1, WithItem2.RULE);
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
    }
}
