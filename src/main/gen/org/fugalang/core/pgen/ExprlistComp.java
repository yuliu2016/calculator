package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * exprlist_comp: 'expr_or_star' (',' 'expr_or_star')* [',']
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

    private List<ExprlistComp2> exprlistComp2List;

    @Override
    protected void buildRule() {
        addRequired(exprOrStar());
        addRequired(exprlistComp2List());
        addOptional(isTokenComma(), ",");
    }

    public ExprOrStar exprOrStar() {
        var element = getItem(0);
        element.failIfAbsent(ExprOrStar.RULE);
        return ExprOrStar.of(element);
    }

    public List<ExprlistComp2> exprlistComp2List() {
        if (exprlistComp2List != null) {
            return exprlistComp2List;
        }
        List<ExprlistComp2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(ExprlistComp2.of(node));
        }
        exprlistComp2List = result == null ? Collections.emptyList() : result;
        return exprlistComp2List;
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
        if (result) parseExprlistComp2List(parseTree, level + 1);
        if (result) parseTree.consumeToken(",");

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseExprlistComp2List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return;
        }
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!ExprlistComp2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
    }

    /**
     * ',' 'expr_or_star'
     */
    public static final class ExprlistComp2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("exprlist_comp:2", RuleType.Conjunction, false);

        public static ExprlistComp2 of(ParseTreeNode node) {
            return new ExprlistComp2(node);
        }

        private ExprlistComp2(ParseTreeNode node) {
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
