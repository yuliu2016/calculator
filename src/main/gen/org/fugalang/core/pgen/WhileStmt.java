package org.fugalang.core.pgen;

// while_stmt: 'while' 'namedexpr_expr' 'suite' ['else' 'suite']
public class WhileStmt {
    public final boolean isTokenWhile;
    public final NamedexprExpr namedexprExpr;
    public final Suite suite;
    public final WhileStmt4Group whileStmt4Group;

    public WhileStmt(
            boolean isTokenWhile,
            NamedexprExpr namedexprExpr,
            Suite suite,
            WhileStmt4Group whileStmt4Group
    ) {
        this.isTokenWhile = isTokenWhile;
        this.namedexprExpr = namedexprExpr;
        this.suite = suite;
        this.whileStmt4Group = whileStmt4Group;
    }
}
