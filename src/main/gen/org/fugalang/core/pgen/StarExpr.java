package org.fugalang.core.pgen;

// star_expr: '*' 'bitwise_or'
public class StarExpr {
    private final boolean isTokenTimes;
    private final BitwiseOr bitwiseOr;

    public StarExpr(
            boolean isTokenTimes,
            BitwiseOr bitwiseOr
    ) {
        this.isTokenTimes = isTokenTimes;
        this.bitwiseOr = bitwiseOr;
    }

    public boolean getIsTokenTimes() {
        return isTokenTimes;
    }

    public BitwiseOr getBitwiseOr() {
        return bitwiseOr;
    }
}
