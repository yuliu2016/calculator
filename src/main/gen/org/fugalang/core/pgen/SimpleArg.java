package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * simple_arg: 'NAME' ['=' 'expr']
 */
public final class SimpleArg extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("simple_arg", RuleType.Conjunction, true);

    public static SimpleArg of(ParseTreeNode node) {
        return new SimpleArg(node);
    }

    private SimpleArg(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired(name());
        addOptional(simpleArg2OrNull());
    }

    public String name() {
        var element = getItem(0);
        element.failIfAbsent(TokenType.NAME);
        return element.asString();
    }

    public SimpleArg2 simpleArg2() {
        var element = getItem(1);
        element.failIfAbsent(SimpleArg2.RULE);
        return SimpleArg2.of(element);
    }

    public SimpleArg2 simpleArg2OrNull() {
        var element = getItem(1);
        if (!element.isPresent(SimpleArg2.RULE)) {
            return null;
        }
        return SimpleArg2.of(element);
    }

    public boolean hasSimpleArg2() {
        var element = getItem(1);
        return element.isPresent(SimpleArg2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken(TokenType.NAME);
        if (result) SimpleArg2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '=' 'expr'
     */
    public static final class SimpleArg2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("simple_arg:2", RuleType.Conjunction, false);

        public static SimpleArg2 of(ParseTreeNode node) {
            return new SimpleArg2(node);
        }

        private SimpleArg2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenAssign(), "=");
            addRequired(expr());
        }

        public boolean isTokenAssign() {
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

            result = parseTree.consumeToken("=");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
