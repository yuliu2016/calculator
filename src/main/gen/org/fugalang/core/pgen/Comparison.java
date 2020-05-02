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

    private List<Comparison2> comparison2List;

    @Override
    protected void buildRule() {
        addRequired("bitwiseOr", bitwiseOr());
        addRequired("comparison2List", comparison2List());
    }

    public BitwiseOr bitwiseOr() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return BitwiseOr.of(element);
    }

    public List<Comparison2> comparison2List() {
        return comparison2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = BitwiseOr.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Comparison2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
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

        @Override
        protected void buildRule() {
            addRequired("compOp", compOp());
            addRequired("bitwiseOr", bitwiseOr());
        }

        public CompOp compOp() {
            var element = getItem(0);
            if (!element.isPresent()) return null;
            return CompOp.of(element);
        }

        public BitwiseOr bitwiseOr() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return BitwiseOr.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = CompOp.parse(parseTree, level + 1);
            result = result && BitwiseOr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
