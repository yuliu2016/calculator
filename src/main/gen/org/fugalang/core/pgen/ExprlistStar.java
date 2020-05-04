package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * exprlist_star: 'expr_or_star' (',' 'expr_or_star')* [',']
 */
public final class ExprlistStar extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("exprlist_star", RuleType.Conjunction, true);

    public static ExprlistStar of(ParseTreeNode node) {
        return new ExprlistStar(node);
    }

    private ExprlistStar(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<ExprlistStar2> exprlistStar2List;

    @Override
    protected void buildRule() {
        addRequired(exprOrStar());
        addRequired(exprlistStar2List());
        addOptional(isTokenComma(), ",");
    }

    public ExprOrStar exprOrStar() {
        var element = getItem(0);
        element.failIfAbsent(ExprOrStar.RULE);
        return ExprOrStar.of(element);
    }

    public List<ExprlistStar2> exprlistStar2List() {
        if (exprlistStar2List != null) {
            return exprlistStar2List;
        }
        List<ExprlistStar2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) result = new ArrayList<>();
            result.add(ExprlistStar2.of(node));
        }
        exprlistStar2List = result == null ? Collections.emptyList() : result;
        return exprlistStar2List;
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

        result = ExprOrStar.parse(parseTree, level + 1);
        parseTree.enterCollection();
        if (result) while (true) {
            var pos = parseTree.position();
            if (!ExprlistStar2.parse(parseTree, level + 1) ||
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
     * ',' 'expr_or_star'
     */
    public static final class ExprlistStar2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("exprlist_star:2", RuleType.Conjunction, false);

        public static ExprlistStar2 of(ParseTreeNode node) {
            return new ExprlistStar2(node);
        }

        private ExprlistStar2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenComma(), ",");
            addRequired(exprOrStar());
        }

        public boolean isTokenComma() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public ExprOrStar exprOrStar() {
            var element = getItem(1);
            element.failIfAbsent(ExprOrStar.RULE);
            return ExprOrStar.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            result = result && ExprOrStar.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
