package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * subscript: 'expr' | ['expr'] ':' ['expr'] ['sliceop']
 */
public final class Subscript extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("subscript", RuleType.Disjunction, true);

    public static Subscript of(ParseTreeNode node) {
        return new Subscript(node);
    }

    private Subscript(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addChoice(expr());
        addChoice(subscript2());
    }

    public Expr expr() {
        var element = getItem(0);
        if (!element.isPresent(Expr.RULE)) {
            return null;
        }
        return Expr.of(element);
    }

    public boolean hasExpr() {
        var element = getItem(0);
        return element.isPresent(Expr.RULE);
    }

    public Subscript2 subscript2() {
        var element = getItem(1);
        if (!element.isPresent(Subscript2.RULE)) {
            return null;
        }
        return Subscript2.of(element);
    }

    public boolean hasSubscript2() {
        var element = getItem(1);
        return element.isPresent(Subscript2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Expr.parse(parseTree, level + 1);
        result = result || Subscript2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ['expr'] ':' ['expr'] ['sliceop']
     */
    public static final class Subscript2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("subscript:2", RuleType.Conjunction, false);

        public static Subscript2 of(ParseTreeNode node) {
            return new Subscript2(node);
        }

        private Subscript2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addOptional(expr());
            addRequired(isTokenColon(), ":");
            addOptional(expr1());
            addOptional(sliceop());
        }

        public Expr expr() {
            var element = getItem(0);
            if (!element.isPresent(Expr.RULE)) {
                return null;
            }
            return Expr.of(element);
        }

        public boolean hasExpr() {
            var element = getItem(0);
            return element.isPresent(Expr.RULE);
        }

        public boolean isTokenColon() {
            var element = getItem(1);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Expr expr1() {
            var element = getItem(2);
            if (!element.isPresent(Expr.RULE)) {
                return null;
            }
            return Expr.of(element);
        }

        public boolean hasExpr1() {
            var element = getItem(2);
            return element.isPresent(Expr.RULE);
        }

        public Sliceop sliceop() {
            var element = getItem(3);
            if (!element.isPresent(Sliceop.RULE)) {
                return null;
            }
            return Sliceop.of(element);
        }

        public boolean hasSliceop() {
            var element = getItem(3);
            return element.isPresent(Sliceop.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            Expr.parse(parseTree, level + 1);
            result = parseTree.consumeToken(":");
            if (result) Expr.parse(parseTree, level + 1);
            if (result) Sliceop.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
