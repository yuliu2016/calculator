package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * targets: ('bitwise_or' | 'star_expr') (',' ('bitwise_or' | 'star_expr'))* [',']
 */
public final class Targets extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("targets", RuleType.Conjunction, true);

    public static Targets of(ParseTreeNode node) {
        return new Targets(node);
    }

    private Targets(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<Targets2> targets2List;

    @Override
    protected void buildRule() {
        addRequired("targets1", targets1());
        addRequired("targets2List", targets2List());
        addRequired("isTokenComma", isTokenComma());
    }

    public Targets1 targets1() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return Targets1.of(element);
    }

    public List<Targets2> targets2List() {
        return targets2List;
    }

    public boolean isTokenComma() {
        var element = getItem(2);
        return element.asBoolean();
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Targets1.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Targets2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
        result = result && parseTree.consumeTokenLiteral(",");

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'bitwise_or' | 'star_expr'
     */
    public static final class Targets1 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("targets:1", RuleType.Disjunction, false);

        public static Targets1 of(ParseTreeNode node) {
            return new Targets1(node);
        }

        private Targets1(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addChoice("bitwiseOr", bitwiseOr());
            addChoice("starExpr", starExpr());
        }

        public BitwiseOr bitwiseOr() {
            var element = getItem(0);
            if (!element.isPresent()) return null;
            return BitwiseOr.of(element);
        }

        public boolean hasBitwiseOr() {
            return bitwiseOr() != null;
        }

        public StarExpr starExpr() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return StarExpr.of(element);
        }

        public boolean hasStarExpr() {
            return starExpr() != null;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = BitwiseOr.parse(parseTree, level + 1);
            result = result || StarExpr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * ',' ('bitwise_or' | 'star_expr')
     */
    public static final class Targets2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("targets:2", RuleType.Conjunction, false);

        public static Targets2 of(ParseTreeNode node) {
            return new Targets2(node);
        }

        private Targets2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenComma", isTokenComma());
            addRequired("targets22", targets22());
        }

        public boolean isTokenComma() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public Targets22 targets22() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return Targets22.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral(",");
            result = result && Targets22.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * 'bitwise_or' | 'star_expr'
     */
    public static final class Targets22 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("targets:2:2", RuleType.Disjunction, false);

        public static Targets22 of(ParseTreeNode node) {
            return new Targets22(node);
        }

        private Targets22(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addChoice("bitwiseOr", bitwiseOr());
            addChoice("starExpr", starExpr());
        }

        public BitwiseOr bitwiseOr() {
            var element = getItem(0);
            if (!element.isPresent()) return null;
            return BitwiseOr.of(element);
        }

        public boolean hasBitwiseOr() {
            return bitwiseOr() != null;
        }

        public StarExpr starExpr() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return StarExpr.of(element);
        }

        public boolean hasStarExpr() {
            return starExpr() != null;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = BitwiseOr.parse(parseTree, level + 1);
            result = result || StarExpr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
