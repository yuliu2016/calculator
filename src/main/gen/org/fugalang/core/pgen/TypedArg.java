package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * typed_arg: 'NAME' [':' 'expr']
 */
public final class TypedArg extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("typed_arg", RuleType.Conjunction);

    public static TypedArg of(ParseTreeNode node) {
        return new TypedArg(node);
    }

    private TypedArg(ParseTreeNode node) {
        super(RULE, node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public TypedArg2 expr() {
        return get(1, TypedArg2::of);
    }

    public boolean hasExpr() {
        return has(1, TypedArg2.RULE);
    }

    /**
     * ':' 'expr'
     */
    public static final class TypedArg2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("typed_arg:2", RuleType.Conjunction);

        public static TypedArg2 of(ParseTreeNode node) {
            return new TypedArg2(node);
        }

        private TypedArg2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Expr expr() {
            return get(1, Expr::of);
        }
    }
}
