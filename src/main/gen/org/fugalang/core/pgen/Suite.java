package org.fugalang.core.pgen;

// suite: ':' 'simple_stmt' | 'block_suite'
public class Suite {
    private final Suite1 suite1;
    private final BlockSuite blockSuite;

    public Suite(
            Suite1 suite1,
            BlockSuite blockSuite
    ) {
        this.suite1 = suite1;
        this.blockSuite = blockSuite;
    }

    public Suite1 getSuite1() {
        return suite1;
    }

    public BlockSuite getBlockSuite() {
        return blockSuite;
    }

    // ':' 'simple_stmt'
    public static class Suite1 {
        private final boolean isTokenColon;
        private final SimpleStmt simpleStmt;

        public Suite1(
                boolean isTokenColon,
                SimpleStmt simpleStmt
        ) {
            this.isTokenColon = isTokenColon;
            this.simpleStmt = simpleStmt;
        }

        public boolean getIsTokenColon() {
            return isTokenColon;
        }

        public SimpleStmt getSimpleStmt() {
            return simpleStmt;
        }
    }
}
