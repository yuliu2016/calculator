package org.fugalang.core.pgen;

// trailer: '(' ['arglist'] ')' | '[' 'subscriptlist' ']' | '.' 'NAME' | 'block_suite'
public class Trailer {
    public final Trailer1 trailer1;
    public final Trailer2 trailer2;
    public final Trailer3 trailer3;
    public final BlockSuite blockSuite;

    public Trailer(
            Trailer1 trailer1,
            Trailer2 trailer2,
            Trailer3 trailer3,
            BlockSuite blockSuite
    ) {
        this.trailer1 = trailer1;
        this.trailer2 = trailer2;
        this.trailer3 = trailer3;
        this.blockSuite = blockSuite;
    }

    // '(' ['arglist'] ')'
    public static class Trailer1 {
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

    // '[' 'subscriptlist' ']'
    public static class Trailer2 {
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

    // '.' 'NAME'
    public static class Trailer3 {
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
}
