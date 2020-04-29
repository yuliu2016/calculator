package org.fugalang.core.pgen;

// trailer: '(' ['arglist'] ')' | '[' 'subscriptlist' ']' | '.' 'NAME' | 'block_suite'
public class Trailer {
    private final Trailer1 trailer1;
    private final Trailer2 trailer2;
    private final Trailer3 trailer3;
    private final BlockSuite blockSuite;

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

    public Trailer1 getTrailer1() {
        return trailer1;
    }

    public Trailer2 getTrailer2() {
        return trailer2;
    }

    public Trailer3 getTrailer3() {
        return trailer3;
    }

    public BlockSuite getBlockSuite() {
        return blockSuite;
    }

    // '(' ['arglist'] ')'
    public static class Trailer1 {
        private final boolean isTokenLpar;
        private final Arglist arglist;
        private final boolean isTokenRpar;

        public Trailer1(
                boolean isTokenLpar,
                Arglist arglist,
                boolean isTokenRpar
        ) {
            this.isTokenLpar = isTokenLpar;
            this.arglist = arglist;
            this.isTokenRpar = isTokenRpar;
        }

        public boolean getIsTokenLpar() {
            return isTokenLpar;
        }

        public Arglist getArglist() {
            return arglist;
        }

        public boolean getIsTokenRpar() {
            return isTokenRpar;
        }
    }

    // '[' 'subscriptlist' ']'
    public static class Trailer2 {
        private final boolean isTokenLsqb;
        private final Subscriptlist subscriptlist;
        private final boolean isTokenRsqb;

        public Trailer2(
                boolean isTokenLsqb,
                Subscriptlist subscriptlist,
                boolean isTokenRsqb
        ) {
            this.isTokenLsqb = isTokenLsqb;
            this.subscriptlist = subscriptlist;
            this.isTokenRsqb = isTokenRsqb;
        }

        public boolean getIsTokenLsqb() {
            return isTokenLsqb;
        }

        public Subscriptlist getSubscriptlist() {
            return subscriptlist;
        }

        public boolean getIsTokenRsqb() {
            return isTokenRsqb;
        }
    }

    // '.' 'NAME'
    public static class Trailer3 {
        private final boolean isTokenDot;
        private final Object name;

        public Trailer3(
                boolean isTokenDot,
                Object name
        ) {
            this.isTokenDot = isTokenDot;
            this.name = name;
        }

        public boolean getIsTokenDot() {
            return isTokenDot;
        }

        public Object getName() {
            return name;
        }
    }
}
