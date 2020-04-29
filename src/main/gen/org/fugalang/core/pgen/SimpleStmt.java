package org.fugalang.core.pgen;

// simple_stmt: 'small_stmt' (';' 'small_stmt')* [';']
public class SimpleStmt {
    public final SmallStmt smallStmt;
    public final SimpleStmt2Group simpleStmt2Group;
    public final boolean isTokenSemicolon;

    public SimpleStmt(
            SmallStmt smallStmt,
            SimpleStmt2Group simpleStmt2Group,
            boolean isTokenSemicolon
    ) {
        this.smallStmt = smallStmt;
        this.simpleStmt2Group = simpleStmt2Group;
        this.isTokenSemicolon = isTokenSemicolon;
    }
}
