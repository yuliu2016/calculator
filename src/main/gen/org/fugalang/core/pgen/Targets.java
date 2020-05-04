package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
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
        addRequired(targets1());
        addRequired(targets2List());
        addOptional(isTokenComma(), ",");
    }

    public Targets1 targets1() {
        var element = getItem(0);
        element.failIfAbsent(Targets1.RULE);
        return Targets1.of(element);
    }

    public List<Targets2> targets2List() {
        if (targets2List != null) {
            return targets2List;
        }
        List<Targets2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) result = new ArrayList<>();
            result.add(Targets2.of(node));
        }
        targets2List = result == null ? Collections.emptyList() : result;
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
        if (result) while (true) {
            var pos = parseTree.position();
            if (!Targets2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
        if (result) parseTree.consumeToken(",");

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
            addChoice(bitwiseOr());
            addChoice(starExpr());
        }

        public BitwiseOr bitwiseOr() {
            var element = getItem(0);
            if (!element.isPresent(BitwiseOr.RULE)) {
                return null;
            }
            return BitwiseOr.of(element);
        }

        public boolean hasBitwiseOr() {
            return bitwiseOr() != null;
        }

        public StarExpr starExpr() {
            var element = getItem(1);
            if (!element.isPresent(StarExpr.RULE)) {
                return null;
            }
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
            addRequired(isTokenComma(), ",");
            addRequired(targets22());
        }

        public boolean isTokenComma() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Targets22 targets22() {
            var element = getItem(1);
            element.failIfAbsent(Targets22.RULE);
            return Targets22.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
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
            addChoice(bitwiseOr());
            addChoice(starExpr());
        }

        public BitwiseOr bitwiseOr() {
            var element = getItem(0);
            if (!element.isPresent(BitwiseOr.RULE)) {
                return null;
            }
            return BitwiseOr.of(element);
        }

        public boolean hasBitwiseOr() {
            return bitwiseOr() != null;
        }

        public StarExpr starExpr() {
            var element = getItem(1);
            if (!element.isPresent(StarExpr.RULE)) {
                return null;
            }
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
