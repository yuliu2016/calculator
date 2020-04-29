package org.fugalang.core.pgen;

import java.util.Optional;

// power: 'pipe_expr' ['**' 'factor']
public class Power {
    private final PipeExpr pipeExpr;
    private final Power2Group power2Group;

    public Power(
            PipeExpr pipeExpr,
            Power2Group power2Group
    ) {
        this.pipeExpr = pipeExpr;
        this.power2Group = power2Group;
    }

    public PipeExpr getPipeExpr() {
        return pipeExpr;
    }

    public Optional<Power2Group> getPower2Group() {
        return Optional.ofNullable(power2Group);
    }

    // '**' 'factor'
    public static class Power2Group {
        private final boolean isTokenPower;
        private final Factor factor;

        public Power2Group(
                boolean isTokenPower,
                Factor factor
        ) {
            this.isTokenPower = isTokenPower;
            this.factor = factor;
        }

        public boolean getIsTokenPower() {
            return isTokenPower;
        }

        public Factor getFactor() {
            return factor;
        }
    }
}
