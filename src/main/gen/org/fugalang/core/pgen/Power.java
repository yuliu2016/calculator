package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// power: 'pipe_expr' ['**' 'factor']
public final class Power extends ConjunctionRule {
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
        addRequired("pipeExpr", pipeExpr);
        addOptional("power2", power2);
    }

    public PipeExpr pipeExpr() {
        return pipeExpr;
    }

    public Optional<Power2> power2() {
        return Optional.ofNullable(power2);
    }

    // '**' 'factor'
    public static final class Power2 extends ConjunctionRule {
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
            addRequired("isTokenPower", isTokenPower);
            addRequired("factor", factor);
        }

        public boolean isTokenPower() {
            return isTokenPower;
        }

        public Factor factor() {
            return factor;
        }
    }
}
