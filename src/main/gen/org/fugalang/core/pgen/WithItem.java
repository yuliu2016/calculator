package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * with_item: 'expr' ['as' 'NAME']
 */
public final class WithItem extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("with_item", RuleType.Conjunction, true);

    private final Expr expr;
    private final WithItem2 withItem2;

    public WithItem(
            Expr expr,
            WithItem2 withItem2
    ) {
        this.expr = expr;
        this.withItem2 = withItem2;
    }

    @Override
    protected void buildRule() {
        addRequired("expr", expr());
        addOptional("withItem2", withItem2());
    }

    public Expr expr() {
        return expr;
    }

    public WithItem2 withItem2() {
        return withItem2;
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
    public static final class WithItem2 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("with_item:2", RuleType.Conjunction, false);

        private final boolean isTokenAs;
        private final String name;

        public WithItem2(
                boolean isTokenAs,
                String name
        ) {
            this.isTokenAs = isTokenAs;
            this.name = name;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenAs", isTokenAs());
            addRequired("name", name());
        }

        public boolean isTokenAs() {
            return isTokenAs;
        }

        public String name() {
            return name;
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
