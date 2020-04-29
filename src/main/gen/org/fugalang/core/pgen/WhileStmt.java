package org.fugalang.core.pgen;

// while_stmt: 'while' 'namedexpr_expr' 'suite' ['else' 'suite']
public class WhileStmt {
    private final boolean isTokenWhile;
    private final NamedexprExpr namedexprExpr;
    private final Suite suite;
    private final WhileStmt4Group whileStmt4Group;

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

    public boolean getIsTokenWhile() {
        return isTokenWhile;
    }

    public NamedexprExpr getNamedexprExpr() {
        return namedexprExpr;
    }

    public Suite getSuite() {
        return suite;
    }

    public WhileStmt4Group getWhileStmt4Group() {
        return whileStmt4Group;
    }

    // 'else' 'suite'
    public static class WhileStmt4Group {
        private final boolean isTokenElse;
        private final Suite suite;

        public WhileStmt4Group(
                boolean isTokenElse,
                Suite suite
        ) {
            this.isTokenElse = isTokenElse;
            this.suite = suite;
        }

        public boolean getIsTokenElse() {
            return isTokenElse;
        }

        public Suite getSuite() {
            return suite;
        }
    }
}
