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

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = TypedArg.parse(t, lv + 1);
        if (r) DefaultArg2.parse(t, lv + 1);
        t.exit(r);
        return r;
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

        public static boolean parse(ParseTree t, int lv) {
            if (t.recursionGuard(lv)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("=");
            r = r && Expr.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
