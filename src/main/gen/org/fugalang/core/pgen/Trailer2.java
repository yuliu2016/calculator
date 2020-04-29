package org.fugalang.core.pgen;

// '[' 'subscriptlist' ']'
public class Trailer2 {
    public final boolean isTokenLsqb;
    public final Subscriptlist subscriptlist;
    public final boolean isTokenRsqb;

    public Trailer2(
            boolean isTokenLsqb,
            Subscriptlist subscriptlist,
            boolean isTokenRsqb
    ) {
        this.isTokenLsqb = isTokenLsqb;
        this.subscriptlist = subscriptlist;
        this.isTokenRsqb = isTokenRsqb;
    }
}
