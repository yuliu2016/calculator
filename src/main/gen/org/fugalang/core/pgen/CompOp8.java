package org.fugalang.core.pgen;

// 'not' 'in'
public class CompOp8 {
    public final boolean isTokenNot;
    public final boolean isTokenIn;

    public CompOp8(
            boolean isTokenNot,
            boolean isTokenIn
    ) {
        this.isTokenNot = isTokenNot;
        this.isTokenIn = isTokenIn;
    }
}
