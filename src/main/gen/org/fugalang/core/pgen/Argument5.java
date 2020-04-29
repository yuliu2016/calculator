package org.fugalang.core.pgen;

// '*' 'expr'
public class Argument5 {
    public final boolean isTokenTimes;
    public final Expr expr;

    public Argument5(
            boolean isTokenTimes,
            Expr expr
    ) {
        this.isTokenTimes = isTokenTimes;
        this.expr = expr;
    }
}
