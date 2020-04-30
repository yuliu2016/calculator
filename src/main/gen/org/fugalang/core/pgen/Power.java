package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// power: 'pipe_expr' ['**' 'factor']
public final class Power extends ConjunctionRule {
    public static final String RULE_NAME = "power";

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
        setExplicitName(RULE_NAME);
        addRequired("pipeExpr", pipeExpr);
        addOptional("power2", power2);
    }

    public PipeExpr pipeExpr() {
        return pipeExpr;
    }

    public Optional<Power2> power2() {
        return Optional.ofNullable(power2);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        var result = false;
        parseTree.exit(level, marker, result);
        return result;
    }

    // '**' 'factor'
    public static final class Power2 extends ConjunctionRule {
        public static final String RULE_NAME = "power:2";

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
            setImpliedName(RULE_NAME);
            addRequired("isTokenPower", isTokenPower);
            addRequired("factor", factor);
        }

        public boolean isTokenPower() {
            return isTokenPower;
        }

        public Factor factor() {
            return factor;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            var result = false;
            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
