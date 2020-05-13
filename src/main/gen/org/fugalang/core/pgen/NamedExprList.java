package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * named_expr_list: 'named_expr_star' (',' 'named_expr_star')* [',']
 */
public final class NamedExprList extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("named_expr_list", RuleType.Conjunction, true);

    public static NamedExprList of(ParseTreeNode node) {
        return new NamedExprList(node);
    }

    private NamedExprList(ParseTreeNode node) {
        super(RULE, node);
    }

    public NamedExprStar namedExprStar() {
        return NamedExprStar.of(getItem(0));
    }

    public List<NamedExprList2> namedExprList2List() {
        return getList(1, NamedExprList2::of);
    }

    public boolean isTokenComma() {
        return getBoolean(2);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = NamedExprStar.parse(parseTree, level + 1);
        if (result) parseNamedExprList2List(parseTree, level + 1);
        if (result) parseTree.consumeToken(",");

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseNamedExprList2List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return;
        }
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!NamedExprList2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
    }

    /**
     * ',' 'named_expr_star'
     */
    public static final class NamedExprList2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("named_expr_list:2", RuleType.Conjunction, false);

        public static NamedExprList2 of(ParseTreeNode node) {
            return new NamedExprList2(node);
        }

        private NamedExprList2(ParseTreeNode node) {
            super(RULE, node);
        }

        public boolean isTokenComma() {
            return true;
        }

        public NamedExprStar namedExprStar() {
            return NamedExprStar.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            result = result && NamedExprStar.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
