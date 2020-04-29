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

    // '{' 'simple_stmt' '}'
    public static class BlockSuite1 {
        public final boolean isTokenLbrace;
        public final SimpleStmt simpleStmt;
        public final boolean isTokenRbrace;

        public BlockSuite1(
                boolean isTokenLbrace,
                SimpleStmt simpleStmt,
                boolean isTokenRbrace
        ) {
            this.isTokenLbrace = isTokenLbrace;
            this.simpleStmt = simpleStmt;
            this.isTokenRbrace = isTokenRbrace;
        }
    }

    // '{' 'NEWLINE' 'stmt'+ '}'
    public static class BlockSuite2 {
        public final boolean isTokenLbrace;
        public final Object newline;
        public final Stmt stmt;
        public final boolean isTokenRbrace;

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
    }
}
