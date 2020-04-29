package org.fugalang.core.pgen;

// 'elif' 'namedexpr_expr' 'suite'
public class IfStmt4Group {
    public final boolean isTokenElif;
    public final NamedexprExpr namedexprExpr;
    public final Suite suite;

    public IfStmt4Group(
            boolean isTokenElif,
            NamedexprExpr namedexprExpr,
            Suite suite
    ) {
        this.isTokenElif = isTokenElif;
        this.namedexprExpr = namedexprExpr;
        this.suite = suite;
    }
}
