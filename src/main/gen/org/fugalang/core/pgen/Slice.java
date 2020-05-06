package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * slice: 'expr' | ['expr'] ':' ['expr'] [':' ['expr']]
 */
public final class Slice extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("slice", RuleType.Disjunction, true);

    public static Slice of(ParseTreeNode node) {
        return new Slice(node);
    }

    private Slice(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addChoice(exprOrNull());
        addChoice(slice2OrNull());
    }

    public Expr expr() {
        var element = getItem(0);
        element.failIfAbsent(Expr.RULE);
        return Expr.of(element);
    }

    public Expr exprOrNull() {
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

    public Slice2 slice2() {
        var element = getItem(1);
        element.failIfAbsent(Slice2.RULE);
        return Slice2.of(element);
    }

    public Slice2 slice2OrNull() {
        var element = getItem(1);
        if (!element.isPresent(Slice2.RULE)) {
            return null;
        }
        return Slice2.of(element);
    }

    public boolean hasSlice2() {
        var element = getItem(1);
        return element.isPresent(Slice2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Expr.parse(parseTree, level + 1);
        result = result || Slice2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ['expr'] ':' ['expr'] [':' ['expr']]
     */
    public static final class Slice2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("slice:2", RuleType.Conjunction, false);

        public static Slice2 of(ParseTreeNode node) {
            return new Slice2(node);
        }

        private Slice2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addOptional(exprOrNull());
            addRequired(isTokenColon(), ":");
            addOptional(expr1OrNull());
            addOptional(slice24OrNull());
        }

        public Expr expr() {
            var element = getItem(0);
            element.failIfAbsent(Expr.RULE);
            return Expr.of(element);
        }

        public Expr exprOrNull() {
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
            element.failIfAbsent(Expr.RULE);
            return Expr.of(element);
        }

        public Expr expr1OrNull() {
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

        public Slice24 slice24() {
            var element = getItem(3);
            element.failIfAbsent(Slice24.RULE);
            return Slice24.of(element);
        }

        public Slice24 slice24OrNull() {
            var element = getItem(3);
            if (!element.isPresent(Slice24.RULE)) {
                return null;
            }
            return Slice24.of(element);
        }

        public boolean hasSlice24() {
            var element = getItem(3);
            return element.isPresent(Slice24.RULE);
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
            if (result) Slice24.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * ':' ['expr']
     */
    public static final class Slice24 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("slice:2:4", RuleType.Conjunction, false);

        public static Slice24 of(ParseTreeNode node) {
            return new Slice24(node);
        }

        private Slice24(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenColon(), ":");
            addOptional(exprOrNull());
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

        public Expr exprOrNull() {
            var element = getItem(1);
            if (!element.isPresent(Expr.RULE)) {
                return null;
            }
            return Expr.of(element);
        }

        public boolean hasExpr() {
            var element = getItem(1);
            return element.isPresent(Expr.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(":");
            if (result) Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
