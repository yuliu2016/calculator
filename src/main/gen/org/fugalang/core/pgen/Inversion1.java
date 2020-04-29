package org.fugalang.core.pgen;

// 'not' 'inversion'
public class Inversion1 {
    public final boolean isTokenNot;
    public final Inversion inversion;

    public Inversion1(
            boolean isTokenNot,
            Inversion inversion
    ) {
        this.isTokenNot = isTokenNot;
        this.inversion = inversion;
    }
}
