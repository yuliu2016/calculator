package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
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
        addRequired(bitwiseOr());
        addRequired(comparison2List());
    }

    public BitwiseOr bitwiseOr() {
        var element = getItem(0);
        element.failIfAbsent(BitwiseOr.RULE);
        return BitwiseOr.of(element);
    }

    public List<Comparison2> comparison2List() {
        if (comparison2List != null) {
            return comparison2List;
        }
        List<Comparison2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) result = new ArrayList<>();
            result.add(Comparison2.of(node));
        }
        comparison2List = result == null ? Collections.emptyList() : result;
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
        if (result) while (true) {
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
            addRequired(compOp());
            addRequired(bitwiseOr());
        }

        public CompOp compOp() {
            var element = getItem(0);
            element.failIfAbsent(CompOp.RULE);
            return CompOp.of(element);
        }

        public BitwiseOr bitwiseOr() {
            var element = getItem(1);
            element.failIfAbsent(BitwiseOr.RULE);
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
