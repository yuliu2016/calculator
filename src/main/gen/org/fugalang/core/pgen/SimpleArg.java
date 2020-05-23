package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * simple_arg: 'NAME' ['=' 'expr']
 */
public final class SimpleArg extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("simple_arg", RuleType.Conjunction);

    public static SimpleArg of(ParseTreeNode node) {
        return new SimpleArg(node);
    }

    private SimpleArg(ParseTreeNode node) {
        super(RULE, node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public SimpleArg2 expr() {
        return get(1, SimpleArg2::of);
    }

    public boolean hasExpr() {
        return has(1, SimpleArg2.RULE);
    }

    /**
     * '=' 'expr'
     */
    public static final class SimpleArg2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("simple_arg:2", RuleType.Conjunction);

        public static SimpleArg2 of(ParseTreeNode node) {
            return new SimpleArg2(node);
        }

        private SimpleArg2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Expr expr() {
            return get(1, Expr::of);
        }
    }
}
