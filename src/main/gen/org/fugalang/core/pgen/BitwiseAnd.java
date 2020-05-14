package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * bitwise_and: 'shift_expr' ('&' 'shift_expr')*
 */
public final class BitwiseAnd extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("bitwise_and", RuleType.Conjunction, true);

    public static BitwiseAnd of(ParseTreeNode node) {
        return new BitwiseAnd(node);
    }

    private BitwiseAnd(ParseTreeNode node) {
        super(RULE, node);
    }

    public ShiftExpr shiftExpr() {
        return ShiftExpr.of(getItem(0));
    }

    public List<BitwiseAnd2> bitwiseAnd2List() {
        return getList(1, BitwiseAnd2::of);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = ShiftExpr.parse(parseTree, level + 1);
        if (result) parseBitwiseAnd2List(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseBitwiseAnd2List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return;
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!BitwiseAnd2.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
    }

    /**
     * '&' 'shift_expr'
     */
    public static final class BitwiseAnd2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("bitwise_and:2", RuleType.Conjunction, false);

        public static BitwiseAnd2 of(ParseTreeNode node) {
            return new BitwiseAnd2(node);
        }

        private BitwiseAnd2(ParseTreeNode node) {
            super(RULE, node);
        }

        public ShiftExpr shiftExpr() {
            return ShiftExpr.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("&");
            result = result && ShiftExpr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
