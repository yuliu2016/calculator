package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * comparison: 'bitwise_or' ('comp_op' 'bitwise_or')*
 */
public final class Comparison extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("comparison", RuleType.Conjunction, true);

    public static Comparison of(ParseTreeNode node) {
        return new Comparison(node);
    }

    private Comparison(ParseTreeNode node) {
        super(RULE, node);
    }

    public BitwiseOr bitwiseOr() {
        return BitwiseOr.of(getItem(0));
    }

    public List<Comparison2> comparison2List() {
        return getList(1, Comparison2::of);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = BitwiseOr.parse(parseTree, level + 1);
        if (result) parseComparison2List(parseTree, level);

        parseTree.exit(result);
        return result;
    }

    private static void parseComparison2List(ParseTree parseTree, int level) {
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Comparison2.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
    }

    /**
     * 'comp_op' 'bitwise_or'
     */
    public static final class Comparison2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("comparison:2", RuleType.Conjunction, false);

        public static Comparison2 of(ParseTreeNode node) {
            return new Comparison2(node);
        }

        private Comparison2(ParseTreeNode node) {
            super(RULE, node);
        }

        public CompOp compOp() {
            return CompOp.of(getItem(0));
        }

        public BitwiseOr bitwiseOr() {
            return BitwiseOr.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = CompOp.parse(parseTree, level + 1);
            result = result && BitwiseOr.parse(parseTree, level + 1);

            parseTree.exit(result);
            return result;
        }
    }
}
