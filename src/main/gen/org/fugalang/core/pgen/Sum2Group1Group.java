package org.fugalang.core.pgen;

// '+' | '-'
public class Sum2Group1Group {
    public final boolean isTokenPlus;
    public final boolean isTokenMinus;

    public Sum2Group1Group(
            boolean isTokenPlus,
            boolean isTokenMinus
    ) {
        this.isTokenPlus = isTokenPlus;
        this.isTokenMinus = isTokenMinus;
    }
}
