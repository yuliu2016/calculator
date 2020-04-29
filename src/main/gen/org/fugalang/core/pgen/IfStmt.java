package org.fugalang.core.pgen;

// if_stmt: 'if' 'namedexpr_expr' 'suite' ('elif' 'namedexpr_expr' 'suite')* ['else' 'suite']
public class IfStmt {
    public final boolean isTokenIf;
    public final NamedexprExpr namedexprExpr;
    public final Suite suite;
    public final IfStmt4Group ifStmt4Group;
    public final IfStmt5Group ifStmt5Group;

    public IfStmt(
            boolean isTokenIf,
            NamedexprExpr namedexprExpr,
            Suite suite,
            IfStmt4Group ifStmt4Group,
            IfStmt5Group ifStmt5Group
    ) {
        this.isTokenIf = isTokenIf;
        this.namedexprExpr = namedexprExpr;
        this.suite = suite;
        this.ifStmt4Group = ifStmt4Group;
        this.ifStmt5Group = ifStmt5Group;
    }
}
