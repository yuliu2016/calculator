package org.fugalang.core.pgen;

// 'is' 'not'
public class CompOp10 {
    public final boolean isTokenIs;
    public final boolean isTokenNot;

    public CompOp10(
            boolean isTokenIs,
            boolean isTokenNot
    ) {
        this.isTokenIs = isTokenIs;
        this.isTokenNot = isTokenNot;
    }
}
