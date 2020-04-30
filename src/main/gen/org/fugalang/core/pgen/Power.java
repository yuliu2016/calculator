package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// power: 'pipe_expr' ['**' 'factor']
public final class Power extends ConjunctionRule {
    private final PipeExpr pipeExpr;
    private final Power2Group power2Group;

    public Power(
            PipeExpr pipeExpr,
            Power2Group power2Group
    ) {
        this.pipeExpr = pipeExpr;
        this.power2Group = power2Group;

        addRequired("pipeExpr", pipeExpr);
        addOptional("power2Group", power2Group);
    }

    public PipeExpr pipeExpr() {
        return pipeExpr;
    }

    public Optional<Power2Group> power2Group() {
        return Optional.ofNullable(power2Group);
    }

    // '**' 'factor'
    public static final class Power2Group extends ConjunctionRule {
        private final boolean isTokenPower;
        private final Factor factor;

        public Power2Group(
                boolean isTokenPower,
                Factor factor
        ) {
            this.isTokenPower = isTokenPower;
            this.factor = factor;

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
