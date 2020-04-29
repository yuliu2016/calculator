package org.fugalang.core.pgen;

// '+' | '-' | '~'
public class Factor11Group {
    public final boolean isTokenPlus;
    public final boolean isTokenMinus;
    public final boolean isTokenBitNot;

    public Factor11Group(
            boolean isTokenPlus,
            boolean isTokenMinus,
            boolean isTokenBitNot
    ) {
        this.isTokenPlus = isTokenPlus;
        this.isTokenMinus = isTokenMinus;
        this.isTokenBitNot = isTokenBitNot;
    }
}
