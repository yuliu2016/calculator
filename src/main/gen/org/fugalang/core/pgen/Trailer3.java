package org.fugalang.core.pgen;

// '.' 'NAME'
public class Trailer3 {
    public final boolean isTokenDot;
    public final Object name;

    public Trailer3(
            boolean isTokenDot,
            Object name
    ) {
        this.isTokenDot = isTokenDot;
        this.name = name;
    }
}
