package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

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
        addRequired("expr", expr());
        addOptional("withItem2", withItem2());
    }

    public Expr expr() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return Expr.of(element);
    }

    public WithItem2 withItem2() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return WithItem2.of(element);
    }

    public boolean hasWithItem2() {
        return withItem2() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Expr.parse(parseTree, level + 1);
        WithItem2.parse(parseTree, level + 1);

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
            addRequired("isTokenAs", isTokenAs());
            addRequired("name", name());
        }

        public boolean isTokenAs() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public String name() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return (String) element.asObject();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("as");
            result = result && parseTree.consumeTokenType("NAME");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
