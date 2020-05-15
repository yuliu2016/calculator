package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * typed_arg: 'NAME' [':' 'expr']
 */
public final class TypedArg extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("typed_arg", RuleType.Conjunction, true);

    public static TypedArg of(ParseTreeNode node) {
        return new TypedArg(node);
    }

    private TypedArg(ParseTreeNode node) {
        super(RULE, node);
    }

    public String name() {
        return getItemOfType(0, TokenType.NAME);
    }

    public TypedArg2 typedArg2() {
        return TypedArg2.of(getItem(1));
    }

    public boolean hasTypedArg2() {
        return hasItemOfRule(1, TypedArg2.RULE);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = t.consumeToken(TokenType.NAME);
        if (r) TypedArg2.parse(t, l + 1);
        t.exit(r);
        return r;
    }

    /**
     * ':' 'expr'
     */
    public static final class TypedArg2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("typed_arg:2", RuleType.Conjunction, false);

        public static TypedArg2 of(ParseTreeNode node) {
            return new TypedArg2(node);
        }

        private TypedArg2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Expr expr() {
            return Expr.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken(":");
            r = r && Expr.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}
