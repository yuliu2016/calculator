package org.fugalang.core.pgen;

// ':' 'expr' | 'block_suite'
public class Funcdef4Group {
    public final Funcdef4Group1 funcdef4Group1;
    public final BlockSuite blockSuite;

    public Funcdef4Group(
            Funcdef4Group1 funcdef4Group1,
            BlockSuite blockSuite
    ) {
        this.funcdef4Group1 = funcdef4Group1;
        this.blockSuite = blockSuite;
    }
}
