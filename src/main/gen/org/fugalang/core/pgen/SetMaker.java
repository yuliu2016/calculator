package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * set_maker: 'expr_or_star' (',' 'expr_or_star')* [',']
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

    public ExprOrStar exprOrStar() {
        return ExprOrStar.of(getItem(0));
    }

    public List<SetMaker2> setMaker2List() {
        return getList(1, SetMaker2::of);
    }

    public boolean isTokenComma() {
        return getBoolean(2);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = ExprOrStar.parse(parseTree, level + 1);
        if (result) parseSetMaker2List(parseTree, level);
        if (result) parseTree.consumeToken(",");

        parseTree.exit(result);
        return result;
    }

    private static void parseSetMaker2List(ParseTree parseTree, int level) {
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!SetMaker2.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
    }

    /**
     * ',' 'expr_or_star'
     */
    public static final class SetMaker2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("set_maker:2", RuleType.Conjunction, false);

        public static SetMaker2 of(ParseTreeNode node) {
            return new SetMaker2(node);
        }

        private SetMaker2(ParseTreeNode node) {
            super(RULE, node);
        }

        public ExprOrStar exprOrStar() {
            return ExprOrStar.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            result = result && ExprOrStar.parse(parseTree, level + 1);

            parseTree.exit(result);
            return result;
        }
    }
}
