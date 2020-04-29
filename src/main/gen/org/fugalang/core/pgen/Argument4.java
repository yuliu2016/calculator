package org.fugalang.core.pgen;

// '**' 'expr'
public class Argument4 {
    public final boolean isTokenPower;
    public final Expr expr;

    public Argument4(
            boolean isTokenPower,
            Expr expr
    ) {
        this.isTokenPower = isTokenPower;
        this.expr = expr;
    }
}
