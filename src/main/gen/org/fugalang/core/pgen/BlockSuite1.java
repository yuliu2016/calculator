package org.fugalang.core.pgen;

// '{' 'simple_stmt' '}'
public class BlockSuite1 {
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
