package org.fugalang.core.pgen;

import java.util.List;

// simple_stmt: 'small_stmt' (';' 'small_stmt')* [';']
public class SimpleStmt {
    public final SmallStmt smallStmt;
    public final List<SimpleStmt2Group> simpleStmt2GroupList;
    public final boolean isTokenSemicolon;

    public SimpleStmt(
            SmallStmt smallStmt,
            List<SimpleStmt2Group> simpleStmt2GroupList,
            boolean isTokenSemicolon
    ) {
        this.smallStmt = smallStmt;
        this.simpleStmt2GroupList = simpleStmt2GroupList;
        this.isTokenSemicolon = isTokenSemicolon;
    }
}
