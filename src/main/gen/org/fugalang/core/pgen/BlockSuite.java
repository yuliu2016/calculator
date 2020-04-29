package org.fugalang.core.pgen;

// block_suite: '{' 'simple_stmt' '}' | '{' 'NEWLINE' 'stmt'+ '}'
public class BlockSuite {
    private final BlockSuite1 blockSuite1;
    private final BlockSuite2 blockSuite2;

    public BlockSuite(
            BlockSuite1 blockSuite1,
            BlockSuite2 blockSuite2
    ) {
        this.blockSuite1 = blockSuite1;
        this.blockSuite2 = blockSuite2;
    }

    public BlockSuite1 getBlockSuite1() {
        return blockSuite1;
    }

    public BlockSuite2 getBlockSuite2() {
        return blockSuite2;
    }

    // '{' 'simple_stmt' '}'
    public static class BlockSuite1 {
        private final boolean isTokenLbrace;
        private final SimpleStmt simpleStmt;
        private final boolean isTokenRbrace;

        public BlockSuite1(
                boolean isTokenLbrace,
                SimpleStmt simpleStmt,
                boolean isTokenRbrace
        ) {
            this.isTokenLbrace = isTokenLbrace;
            this.simpleStmt = simpleStmt;
            this.isTokenRbrace = isTokenRbrace;
        }

        public boolean getIsTokenLbrace() {
            return isTokenLbrace;
        }

        public SimpleStmt getSimpleStmt() {
            return simpleStmt;
        }

        public boolean getIsTokenRbrace() {
            return isTokenRbrace;
        }
    }

    // '{' 'NEWLINE' 'stmt'+ '}'
    public static class BlockSuite2 {
        private final boolean isTokenLbrace;
        private final Object newline;
        private final Stmt stmt;
        private final boolean isTokenRbrace;

        public BlockSuite2(
                boolean isTokenLbrace,
                Object newline,
                Stmt stmt,
                boolean isTokenRbrace
        ) {
            this.isTokenLbrace = isTokenLbrace;
            this.newline = newline;
            this.stmt = stmt;
            this.isTokenRbrace = isTokenRbrace;
        }

        public boolean getIsTokenLbrace() {
            return isTokenLbrace;
        }

        public Object getNewline() {
            return newline;
        }

        public Stmt getStmt() {
            return stmt;
        }

        public boolean getIsTokenRbrace() {
            return isTokenRbrace;
        }
    }
}
