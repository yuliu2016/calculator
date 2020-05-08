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

    @Override
    protected void buildRule() {
        addRequired(name());
        addOptional(typedArg2OrNull());
    }

    public String name() {
        var element = getItem(0);
        element.failIfAbsent(TokenType.NAME);
        return element.asString();
    }

    public TypedArg2 typedArg2() {
        var element = getItem(1);
        element.failIfAbsent(TypedArg2.RULE);
        return TypedArg2.of(element);
    }

    public TypedArg2 typedArg2OrNull() {
        var element = getItem(1);
        if (!element.isPresent(TypedArg2.RULE)) {
            return null;
        }
        return TypedArg2.of(element);
    }

    public boolean hasTypedArg2() {
        var element = getItem(1);
        return element.isPresent(TypedArg2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken(TokenType.NAME);
        if (result) TypedArg2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
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

        @Override
        protected void buildRule() {
            addRequired(isTokenColon(), ":");
            addRequired(expr());
        }

        public boolean isTokenColon() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Expr expr() {
            var element = getItem(1);
            element.failIfAbsent(Expr.RULE);
            return Expr.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(":");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
