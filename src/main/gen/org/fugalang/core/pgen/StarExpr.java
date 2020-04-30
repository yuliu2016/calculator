package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;

// star_expr: '*' 'bitwise_or'
public final class StarExpr extends ConjunctionRule {
    private final boolean isTokenTimes;
    private final BitwiseOr bitwiseOr;

    public StarExpr(
            boolean isTokenTimes,
            BitwiseOr bitwiseOr
    ) {
        this.isTokenTimes = isTokenTimes;
        this.bitwiseOr = bitwiseOr;

        addRequired("isTokenTimes", isTokenTimes);
        addRequired("bitwiseOr", bitwiseOr);
    }

    public boolean isTokenTimes() {
        return isTokenTimes;
    }

    public BitwiseOr bitwiseOr() {
        return bitwiseOr;
    }
}
