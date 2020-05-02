package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * set_maker: 'expr_or_star' ('comp_for' | (',' 'expr_or_star')* [','])
 */
public final class SetMaker extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("set_maker", RuleType.Conjunction, true);

    public static SetMaker of(ParseTreeNode node) {
        return new SetMaker(node);
    }

    private SetMaker(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired("exprOrStar", exprOrStar());
        addRequired("setMaker2", setMaker2());
    }

    public ExprOrStar exprOrStar() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return ExprOrStar.of(element);
    }

    public SetMaker2 setMaker2() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return SetMaker2.of(element);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = ExprOrStar.parse(parseTree, level + 1);
        result = result && SetMaker2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'comp_for' | (',' 'expr_or_star')* [',']
     */
    public static final class SetMaker2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("set_maker:2", RuleType.Disjunction, false);

        public static SetMaker2 of(ParseTreeNode node) {
            return new SetMaker2(node);
        }

        private SetMaker2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addChoice("compFor", compFor());
            addChoice("setMaker22", setMaker22());
        }

        public CompFor compFor() {
            var element = getItem(0);
            if (!element.isPresent()) return null;
            return CompFor.of(element);
        }

        public boolean hasCompFor() {
            return compFor() != null;
        }

        public SetMaker22 setMaker22() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return SetMaker22.of(element);
        }

        public boolean hasSetMaker22() {
            return setMaker22() != null;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = CompFor.parse(parseTree, level + 1);
            result = result || SetMaker22.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * (',' 'expr_or_star')* [',']
     */
    public static final class SetMaker22 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("set_maker:2:2", RuleType.Conjunction, false);

        public static SetMaker22 of(ParseTreeNode node) {
            return new SetMaker22(node);
        }

        private SetMaker22(ParseTreeNode node) {
            super(RULE, node);
        }

        private List<SetMaker221> setMaker221List;

        @Override
        protected void buildRule() {
            addRequired("setMaker221List", setMaker221List());
            addRequired("isTokenComma", isTokenComma());
        }

        public List<SetMaker221> setMaker221List() {
            return setMaker221List;
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
                if (!SetMaker221.parse(parseTree, level + 1) ||
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
    public static final class SetMaker221 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("set_maker:2:2:1", RuleType.Conjunction, false);

        public static SetMaker221 of(ParseTreeNode node) {
            return new SetMaker221(node);
        }

        private SetMaker221(ParseTreeNode node) {
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
