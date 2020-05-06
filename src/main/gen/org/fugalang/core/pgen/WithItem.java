package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * with_item: 'expr' ['as' 'NAME']
 */
public final class WithItem extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("with_item", RuleType.Conjunction, true);

    public static WithItem of(ParseTreeNode node) {
        return new WithItem(node);
    }

    private WithItem(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired(expr());
        addOptional(withItem2());
    }

    public Expr expr() {
        var element = getItem(0);
        element.failIfAbsent(Expr.RULE);
        return Expr.of(element);
    }

    public WithItem2 withItem2() {
        var element = getItem(1);
        element.failIfAbsent(WithItem2.RULE);
        return WithItem2.of(element);
    }

    public WithItem2 withItem2OrNull() {
        var element = getItem(1);
        if (!element.isPresent(WithItem2.RULE)) {
            return null;
        }
        return WithItem2.of(element);
    }

    public boolean hasWithItem2() {
        var element = getItem(1);
        return element.isPresent(WithItem2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Expr.parse(parseTree, level + 1);
        if (result) WithItem2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'as' 'NAME'
     */
    public static final class WithItem2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("with_item:2", RuleType.Conjunction, false);

        public static WithItem2 of(ParseTreeNode node) {
            return new WithItem2(node);
        }

        private WithItem2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenAs(), "as");
            addRequired(name());
        }

        public boolean isTokenAs() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public String name() {
            var element = getItem(1);
            element.failIfAbsent(TokenType.NAME);
            return element.asString();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("as");
            result = result && parseTree.consumeToken(TokenType.NAME);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
