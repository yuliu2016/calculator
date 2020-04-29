package org.fugalang.core.pgen;

import java.util.List;

// if_stmt: 'if' 'namedexpr_expr' 'suite' ('elif' 'namedexpr_expr' 'suite')* ['else' 'suite']
public class IfStmt {
    public final boolean isTokenIf;
    public final NamedexprExpr namedexprExpr;
    public final Suite suite;
    public final List<IfStmt4Group> ifStmt4GroupList;
    public final IfStmt5Group ifStmt5Group;

    public IfStmt(
            boolean isTokenIf,
            NamedexprExpr namedexprExpr,
            Suite suite,
            List<IfStmt4Group> ifStmt4GroupList,
            IfStmt5Group ifStmt5Group
    ) {
        this.isTokenIf = isTokenIf;
        this.namedexprExpr = namedexprExpr;
        this.suite = suite;
        this.ifStmt4GroupList = ifStmt4GroupList;
        this.ifStmt5Group = ifStmt5Group;
    }
}
