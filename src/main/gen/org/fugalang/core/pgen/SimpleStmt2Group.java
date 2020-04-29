package org.fugalang.core.pgen;

// ';' 'small_stmt'
public class SimpleStmt2Group {
    public final boolean isTokenSemicolon;
    public final SmallStmt smallStmt;

    public SimpleStmt2Group(
            boolean isTokenSemicolon,
            SmallStmt smallStmt
    ) {
        this.isTokenSemicolon = isTokenSemicolon;
        this.smallStmt = smallStmt;
    }
}
