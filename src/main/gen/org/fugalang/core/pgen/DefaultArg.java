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
        return TypedArg.of(getItem(0));
    }

    public DefaultArg2 defaultArg2() {
        return DefaultArg2.of(getItem(1));
    }

    public boolean hasDefaultArg2() {
        return hasItemOfRule(1, DefaultArg2.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
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
            return Expr.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consumeToken("=");
            r = r && Expr.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
