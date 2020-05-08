package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * exprlist: 'expr' (',' 'expr')* [',']
 */
public final class Exprlist extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("exprlist", RuleType.Conjunction, true);

    public static Exprlist of(ParseTreeNode node) {
        return new Exprlist(node);
    }

    private Exprlist(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<Exprlist2> exprlist2List;

    @Override
    protected void buildRule() {
        addRequired(expr());
        addRequired(exprlist2List());
        addOptional(isTokenComma(), ",");
    }

    public Expr expr() {
        var element = getItem(0);
        element.failIfAbsent(Expr.RULE);
        return Expr.of(element);
    }

    public List<Exprlist2> exprlist2List() {
        if (exprlist2List != null) {
            return exprlist2List;
        }
        List<Exprlist2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(Exprlist2.of(node));
        }
        exprlist2List = result == null ? Collections.emptyList() : result;
        return exprlist2List;
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

        result = Expr.parse(parseTree, level + 1);
        if (result) parseExprlist2List(parseTree, level + 1);
        if (result) parseTree.consumeToken(",");

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseExprlist2List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return;
        }
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Exprlist2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
    }

    /**
     * ',' 'expr'
     */
    public static final class Exprlist2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("exprlist:2", RuleType.Conjunction, false);

        public static Exprlist2 of(ParseTreeNode node) {
            return new Exprlist2(node);
        }

        private Exprlist2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenComma(), ",");
            addRequired(expr());
        }

        public boolean isTokenComma() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Expr expr() {
            var element = getItem(1);
            element.failIfAbsent(Expr.RULE);
            return Expr.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
