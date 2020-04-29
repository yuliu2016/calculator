package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.DisjunctionRule;

// suite: ':' 'simple_stmt' | 'block_suite'
public final class Suite extends DisjunctionRule {
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
    public static final class Suite1 extends ConjunctionRule {
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
