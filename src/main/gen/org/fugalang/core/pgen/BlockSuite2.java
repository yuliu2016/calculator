package org.fugalang.core.pgen;

// '{' 'NEWLINE' 'stmt'+ '}'
public class BlockSuite2 {
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
