package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * power: 'pipe_expr' ['**' 'factor']
 */
public final class Power extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("power", RuleType.Conjunction, true);

    public static Power of(ParseTreeNode node) {
        return new Power(node);
    }

    private Power(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired(pipeExpr());
        addOptional(power2());
    }

    public PipeExpr pipeExpr() {
        var element = getItem(0);
        element.failIfAbsent(PipeExpr.RULE);
        return PipeExpr.of(element);
    }

    public Power2 power2() {
        var element = getItem(1);
        if (!element.isPresent(Power2.RULE)) {
            return null;
        }
        return Power2.of(element);
    }

    public boolean hasPower2() {
        return power2() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = PipeExpr.parse(parseTree, level + 1);
        if (result) Power2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '**' 'factor'
     */
    public static final class Power2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("power:2", RuleType.Conjunction, false);

        public static Power2 of(ParseTreeNode node) {
            return new Power2(node);
        }

        private Power2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenPower(), "**");
            addRequired(factor());
        }

        public boolean isTokenPower() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Factor factor() {
            var element = getItem(1);
            element.failIfAbsent(Factor.RULE);
            return Factor.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("**");
            result = result && Factor.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
