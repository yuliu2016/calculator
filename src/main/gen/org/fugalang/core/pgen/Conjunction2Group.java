package org.fugalang.core.pgen;

// 'and' 'inversion'
public class Conjunction2Group {
    public final boolean isTokenAnd;
    public final Inversion inversion;

    public Conjunction2Group(
            boolean isTokenAnd,
            Inversion inversion
    ) {
        this.isTokenAnd = isTokenAnd;
        this.inversion = inversion;
    }
}
