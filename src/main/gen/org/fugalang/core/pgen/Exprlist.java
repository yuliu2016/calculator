package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

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

    public Expr expr() {
        return Expr.of(getItem(0));
    }

    public List<Exprlist2> exprlist2List() {
        return getList(1, Exprlist2::of);
    }

    public boolean isTokenComma() {
        return getBoolean(2);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Expr.parse(parseTree, level + 1);
        if (result) parseExprlist2List(parseTree, level + 1);
        if (result) parseTree.consumeToken(",");

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseExprlist2List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return;
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Exprlist2.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
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

        public Expr expr() {
            return Expr.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
