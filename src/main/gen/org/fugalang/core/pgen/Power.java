package org.fugalang.core.pgen;

// power: 'pipe_expr' ['**' 'factor']
public class Power {
    public final PipeExpr pipeExpr;
    public final Power2Group power2Group;

    public Power(
            PipeExpr pipeExpr,
            Power2Group power2Group
    ) {
        this.pipeExpr = pipeExpr;
        this.power2Group = power2Group;
    }
}
