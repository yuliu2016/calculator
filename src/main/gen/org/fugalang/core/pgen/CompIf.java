package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// comp_if: 'if' 'expr' ['comp_iter']
public final class CompIf extends ConjunctionRule {
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

    @Override
    protected void buildRule() {
        addRequired("isTokenIf", isTokenIf);
        addRequired("expr", expr);
        addOptional("compIter", compIter);
    }

    public boolean isTokenIf() {
        return isTokenIf;
    }

    public Expr expr() {
        return expr;
    }

    public Optional<CompIter> compIter() {
        return Optional.ofNullable(compIter);
    }
}
