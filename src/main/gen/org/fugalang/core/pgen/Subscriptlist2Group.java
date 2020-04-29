package org.fugalang.core.pgen;

// ',' 'subscript'
public class Subscriptlist2Group {
    public final boolean isTokenComma;
    public final Subscript subscript;

    public Subscriptlist2Group(
            boolean isTokenComma,
            Subscript subscript
    ) {
        this.isTokenComma = isTokenComma;
        this.subscript = subscript;
    }
}
