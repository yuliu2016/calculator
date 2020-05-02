package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * comparison: 'bitwise_or' ('comp_op' 'bitwise_or')*
 */
public final class Comparison extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("comparison", RuleType.Conjunction, true);

    private final BitwiseOr bitwiseOr;
    private final List<Comparison2> comparison2List;

    public Comparison(
            BitwiseOr bitwiseOr,
            List<Comparison2> comparison2List
    ) {
        this.bitwiseOr = bitwiseOr;
        this.comparison2List = comparison2List;
    }

    @Override
    protected void buildRule() {
        addRequired("bitwiseOr", bitwiseOr());
        addRequired("comparison2List", comparison2List());
    }

    public BitwiseOr bitwiseOr() {
        return bitwiseOr;
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
    public static final class Comparison2 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("comparison:2", RuleType.Conjunction, false);

        private final CompOp compOp;
        private final BitwiseOr bitwiseOr;

        public Comparison2(
                CompOp compOp,
                BitwiseOr bitwiseOr
        ) {
            this.compOp = compOp;
            this.bitwiseOr = bitwiseOr;
        }

        @Override
        protected void buildRule() {
            addRequired("compOp", compOp());
            addRequired("bitwiseOr", bitwiseOr());
        }

        public CompOp compOp() {
            return compOp;
        }

        public BitwiseOr bitwiseOr() {
            return bitwiseOr;
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
