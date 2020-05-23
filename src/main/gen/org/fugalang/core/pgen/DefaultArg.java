package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * default_arg: 'typed_arg' ['=' 'expr']
 */
public final class DefaultArg extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("default_arg", RuleType.Conjunction);

    public static DefaultArg of(ParseTreeNode node) {
        return new DefaultArg(node);
    }

    private DefaultArg(ParseTreeNode node) {
        super(RULE, node);
    }

    public TypedArg typedArg() {
        return get(0, TypedArg::of);
    }

    public DefaultArg2 expr() {
        return get(1, DefaultArg2::of);
    }

    public boolean hasExpr() {
        return has(1, DefaultArg2.RULE);
    }

    /**
     * '=' 'expr'
     */
    public static final class DefaultArg2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("default_arg:2", RuleType.Conjunction);

        public static DefaultArg2 of(ParseTreeNode node) {
            return new DefaultArg2(node);
        }

        private DefaultArg2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Expr expr() {
            return get(1, Expr::of);
        }
    }
}
