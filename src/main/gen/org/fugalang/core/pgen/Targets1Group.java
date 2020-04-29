package org.fugalang.core.pgen;

// 'bitwise_or' | 'star_expr'
public class Targets1Group {
    public final BitwiseOr bitwiseOr;
    public final StarExpr starExpr;

    public Targets1Group(
            BitwiseOr bitwiseOr,
            StarExpr starExpr
    ) {
        this.bitwiseOr = bitwiseOr;
        this.starExpr = starExpr;
    }
}
