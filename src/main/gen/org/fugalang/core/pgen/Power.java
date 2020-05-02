package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * power: 'pipe_expr' ['**' 'factor']
 */
public final class Power extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("power", RuleType.Conjunction, true);

    private final PipeExpr pipeExpr;
    private final Power2 power2;

    public Power(
            PipeExpr pipeExpr,
            Power2 power2
    ) {
        this.pipeExpr = pipeExpr;
        this.power2 = power2;
    }

    @Override
    protected void buildRule() {
        addRequired("pipeExpr", pipeExpr());
        addOptional("power2", power2());
    }

    public PipeExpr pipeExpr() {
        return pipeExpr;
    }

    public Power2 power2() {
        return power2;
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
        Power2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '**' 'factor'
     */
    public static final class Power2 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("power:2", RuleType.Conjunction, false);

        private final boolean isTokenPower;
        private final Factor factor;

        public Power2(
                boolean isTokenPower,
                Factor factor
        ) {
            this.isTokenPower = isTokenPower;
            this.factor = factor;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenPower", isTokenPower());
            addRequired("factor", factor());
        }

        public boolean isTokenPower() {
            return isTokenPower;
        }

        public Factor factor() {
            return factor;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("**");
            result = result && Factor.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
