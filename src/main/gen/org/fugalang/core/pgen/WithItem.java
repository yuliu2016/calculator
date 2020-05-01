package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// with_item: 'expr' ['as' 'NAME']
public final class WithItem extends ConjunctionRule {
    public static final String RULE_NAME = "with_item";

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
        setExplicitName(RULE_NAME);
        addRequired("expr", expr);
        addOptional("withItem2", withItem2);
    }

    public Expr expr() {
        return expr;
    }

    public Optional<WithItem2> withItem2() {
        return Optional.ofNullable(withItem2);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = Expr.parse(parseTree, level + 1);
        WithItem2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    // 'as' 'NAME'
    public static final class WithItem2 extends ConjunctionRule {
        public static final String RULE_NAME = "with_item:2";

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
            setImpliedName(RULE_NAME);
            addRequired("isTokenAs", isTokenAs);
            addRequired("name", name);
        }

        public boolean isTokenAs() {
            return isTokenAs;
        }

        public String name() {
            return name;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("as");
            result = result && parseTree.consumeTokenType("NAME");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
