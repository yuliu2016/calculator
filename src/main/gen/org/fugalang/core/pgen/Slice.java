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

    public Expr expr() {
        return Expr.of(getItem(0));
    }

    public boolean hasExpr() {
        return hasItemOfRule(0, Expr.RULE);
    }

    public Slice2 slice2() {
        return Slice2.of(getItem(1));
    }

    public boolean hasSlice2() {
        return hasItemOfRule(1, Slice2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
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

        public Expr expr() {
            return Expr.of(getItem(0));
        }

        public boolean hasExpr() {
            return hasItemOfRule(0, Expr.RULE);
        }

        public Expr expr1() {
            return Expr.of(getItem(2));
        }

        public boolean hasExpr1() {
            return hasItemOfRule(2, Expr.RULE);
        }

        public Slice24 slice24() {
            return Slice24.of(getItem(3));
        }

        public boolean hasSlice24() {
            return hasItemOfRule(3, Slice24.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
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

        public Expr expr() {
            return Expr.of(getItem(1));
        }

        public boolean hasExpr() {
            return hasItemOfRule(1, Expr.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(":");
            if (result) Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
