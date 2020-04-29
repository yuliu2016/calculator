package org.fugalang.core.pgen;

// 'bitwise_or' | 'star_expr'
public class Targets2Group2Group {
    public final BitwiseOr bitwiseOr;
    public final StarExpr starExpr;

    public Targets2Group2Group(
            BitwiseOr bitwiseOr,
            StarExpr starExpr
    ) {
        this.bitwiseOr = bitwiseOr;
        this.starExpr = starExpr;
    }
}
