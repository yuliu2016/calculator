package org.fugalang.core.pgen;

// '<<' | '>>'
public class ShiftExpr2Group1Group {
    public final boolean isTokenLshift;
    public final boolean isTokenRshift;

    public ShiftExpr2Group1Group(
            boolean isTokenLshift,
            boolean isTokenRshift
    ) {
        this.isTokenLshift = isTokenLshift;
        this.isTokenRshift = isTokenRshift;
    }
}
