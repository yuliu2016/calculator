package org.fugalang.core.pgen;

// comp_if: 'if' 'expr' ['comp_iter']
public class CompIf {
    private final boolean isTokenIf;
    private final Expr expr;
    private final CompIter compIter;

    public CompIf(
            boolean isTokenIf,
            Expr expr,
            CompIter compIter
    ) {
        this.isTokenIf = isTokenIf;
        this.expr = expr;
        this.compIter = compIter;
    }

    public boolean getIsTokenIf() {
        return isTokenIf;
    }

    public Expr getExpr() {
        return expr;
    }

    public CompIter getCompIter() {
        return compIter;
    }
}
