package org.fugalang.core.pgen;

// suite: ':' 'simple_stmt' | 'block_suite'
public class Suite {
    public final Suite1 suite1;
    public final BlockSuite blockSuite;

    public Suite(
            Suite1 suite1,
            BlockSuite blockSuite
    ) {
        this.suite1 = suite1;
        this.blockSuite = blockSuite;
    }
}
