package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * exprlist_comp: 'expr_or_star' ('comp_for' | (',' 'expr_or_star')* [','])
 */
public final class ExprlistComp extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("exprlist_comp", RuleType.Conjunction, true);

    public static ExprlistComp of(ParseTreeNode node) {
        return new ExprlistComp(node);
    }

    private ExprlistComp(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired("exprOrStar", exprOrStar());
        addRequired("exprlistComp2", exprlistComp2());
    }

    public ExprOrStar exprOrStar() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return ExprOrStar.of(element);
    }

    public ExprlistComp2 exprlistComp2() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return ExprlistComp2.of(element);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = ExprOrStar.parse(parseTree, level + 1);
        result = result && ExprlistComp2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'comp_for' | (',' 'expr_or_star')* [',']
     */
    public static final class ExprlistComp2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("exprlist_comp:2", RuleType.Disjunction, false);

        public static ExprlistComp2 of(ParseTreeNode node) {
            return new ExprlistComp2(node);
        }

        private ExprlistComp2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addChoice("compFor", compFor());
            addChoice("exprlistComp22", exprlistComp22());
        }

        public CompFor compFor() {
            var element = getItem(0);
            if (!element.isPresent()) return null;
            return CompFor.of(element);
        }

        public boolean hasCompFor() {
            return compFor() != null;
        }

        public ExprlistComp22 exprlistComp22() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return ExprlistComp22.of(element);
        }

        public boolean hasExprlistComp22() {
            return exprlistComp22() != null;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = CompFor.parse(parseTree, level + 1);
            result = result || ExprlistComp22.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * (',' 'expr_or_star')* [',']
     */
    public static final class ExprlistComp22 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("exprlist_comp:2:2", RuleType.Conjunction, false);

        public static ExprlistComp22 of(ParseTreeNode node) {
            return new ExprlistComp22(node);
        }

        private ExprlistComp22(ParseTreeNode node) {
            super(RULE, node);
        }

        private List<ExprlistComp221> exprlistComp221List;

        @Override
        protected void buildRule() {
            addRequired("exprlistComp221List", exprlistComp221List());
            addRequired("isTokenComma", isTokenComma());
        }

        public List<ExprlistComp221> exprlistComp221List() {
            if (exprlistComp221List != null) {
                return exprlistComp221List;
            }
            List<ExprlistComp221> result = null;
            var element = getItem(0);
            for (var node : element.asCollection()) {
                if (result == null) result = new ArrayList<>();
                result.add(ExprlistComp221.of(node));
            }
            exprlistComp221List = result == null ? Collections.emptyList() : result;
            return exprlistComp221List;
        }

        public boolean isTokenComma() {
            var element = getItem(1);
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            parseTree.enterCollection();
            while (true) {
                var pos = parseTree.position();
                if (!ExprlistComp221.parse(parseTree, level + 1) ||
                        parseTree.guardLoopExit(pos)) {
                    break;
                }
            }
            parseTree.exitCollection();
            result = parseTree.consumeTokenLiteral(",");

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * ',' 'expr_or_star'
     */
    public static final class ExprlistComp221 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("exprlist_comp:2:2:1", RuleType.Conjunction, false);

        public static ExprlistComp221 of(ParseTreeNode node) {
            return new ExprlistComp221(node);
        }

        private ExprlistComp221(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenComma", isTokenComma());
            addRequired("exprOrStar", exprOrStar());
        }

        public boolean isTokenComma() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public ExprOrStar exprOrStar() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return ExprOrStar.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral(",");
            result = result && ExprOrStar.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
