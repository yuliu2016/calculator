package org.fugalang.core.pgen;

// block_suite: '{' 'simple_stmt' '}' | '{' 'NEWLINE' 'stmt'+ '}'
public class BlockSuite {
    public final BlockSuite1 blockSuite1;
    public final BlockSuite2 blockSuite2;

    public BlockSuite(
            BlockSuite1 blockSuite1,
            BlockSuite2 blockSuite2
    ) {
        this.blockSuite1 = blockSuite1;
        this.blockSuite2 = blockSuite2;
    }
}
