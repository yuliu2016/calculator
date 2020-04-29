package org.fugalang.core.pgen;

// '(' ['arglist'] ')'
public class Trailer1 {
    public final boolean isTokenLpar;
    public final Arglist arglist;
    public final boolean isTokenRpar;

    public Trailer1(
            boolean isTokenLpar,
            Arglist arglist,
            boolean isTokenRpar
    ) {
        this.isTokenLpar = isTokenLpar;
        this.arglist = arglist;
        this.isTokenRpar = isTokenRpar;
    }
}
