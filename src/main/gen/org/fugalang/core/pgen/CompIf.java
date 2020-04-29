package org.fugalang.core.pgen;

// comp_if: 'if' 'expr' ['comp_iter']
public class CompIf {
    public final boolean isTokenIf;
    public final Expr expr;
    public final CompIter compIter;

    public CompIf(
            boolean isTokenIf,
            Expr expr,
            CompIter compIter
    ) {
        this.isTokenIf = isTokenIf;
        this.expr = expr;
        this.compIter = compIter;
    }
}
