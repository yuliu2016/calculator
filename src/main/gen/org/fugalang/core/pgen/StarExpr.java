package org.fugalang.core.pgen;

// star_expr: '*' 'bitwise_or'
public class StarExpr {
    public final boolean isTokenTimes;
    public final BitwiseOr bitwiseOr;

    public StarExpr(
            boolean isTokenTimes,
            BitwiseOr bitwiseOr
    ) {
        this.isTokenTimes = isTokenTimes;
        this.bitwiseOr = bitwiseOr;
    }
}
