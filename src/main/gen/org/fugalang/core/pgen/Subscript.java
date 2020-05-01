package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;
import org.fugalang.core.parser.DisjunctionRule;

// subscript: 'expr' | ['expr'] ':' ['expr'] ['sliceop']
public final class Subscript extends DisjunctionRule {
    public static final String RULE_NAME = "subscript";

    private final Expr expr;
    private final Subscript2 subscript2;

    public Subscript(
            Expr expr,
            Subscript2 subscript2
    ) {
        this.expr = expr;
        this.subscript2 = subscript2;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addChoice("expr", expr);
        addChoice("subscript2", subscript2);
    }

    public Expr expr() {
        return expr;
    }

    public Subscript2 subscript2() {
        return subscript2;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = Expr.parse(parseTree, level + 1);
        result = result || Subscript2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    // ['expr'] ':' ['expr'] ['sliceop']
    public static final class Subscript2 extends ConjunctionRule {
        public static final String RULE_NAME = "subscript:2";

        private final Expr expr;
        private final boolean isTokenColon;
        private final Expr expr1;
        private final Sliceop sliceop;

        public Subscript2(
                Expr expr,
                boolean isTokenColon,
                Expr expr1,
                Sliceop sliceop
        ) {
            this.expr = expr;
            this.isTokenColon = isTokenColon;
            this.expr1 = expr1;
            this.sliceop = sliceop;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addOptional("expr", expr);
            addRequired("isTokenColon", isTokenColon);
            addOptional("expr1", expr1);
            addOptional("sliceop", sliceop);
        }

        public Optional<Expr> expr() {
            return Optional.ofNullable(expr);
        }

        public boolean isTokenColon() {
            return isTokenColon;
        }

        public Optional<Expr> expr1() {
            return Optional.ofNullable(expr1);
        }

        public Optional<Sliceop> sliceop() {
            return Optional.ofNullable(sliceop);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            Expr.parse(parseTree, level + 1);
            result = parseTree.consumeTokenLiteral(":");
            Expr.parse(parseTree, level + 1);
            Sliceop.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
