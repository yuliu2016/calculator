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
}
