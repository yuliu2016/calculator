package org.fugalang.core.pgen;

import java.util.List;

// if_stmt: 'if' 'namedexpr_expr' 'suite' ('elif' 'namedexpr_expr' 'suite')* ['else' 'suite']
public class IfStmt {
    private final boolean isTokenIf;
    private final NamedexprExpr namedexprExpr;
    private final Suite suite;
    private final List<IfStmt4Group> ifStmt4GroupList;
    private final IfStmt5Group ifStmt5Group;

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

    public boolean getIsTokenIf() {
        return isTokenIf;
    }

    public NamedexprExpr getNamedexprExpr() {
        return namedexprExpr;
    }

    public Suite getSuite() {
        return suite;
    }

    public List<IfStmt4Group> getIfStmt4GroupList() {
        return ifStmt4GroupList;
    }

    public IfStmt5Group getIfStmt5Group() {
        return ifStmt5Group;
    }

    // 'elif' 'namedexpr_expr' 'suite'
    public static class IfStmt4Group {
        private final boolean isTokenElif;
        private final NamedexprExpr namedexprExpr;
        private final Suite suite;

        public IfStmt4Group(
                boolean isTokenElif,
                NamedexprExpr namedexprExpr,
                Suite suite
        ) {
            this.isTokenElif = isTokenElif;
            this.namedexprExpr = namedexprExpr;
            this.suite = suite;
        }

        public boolean getIsTokenElif() {
            return isTokenElif;
        }

        public NamedexprExpr getNamedexprExpr() {
            return namedexprExpr;
        }

        public Suite getSuite() {
            return suite;
        }
    }

    // 'else' 'suite'
    public static class IfStmt5Group {
        private final boolean isTokenElse;
        private final Suite suite;

        public IfStmt5Group(
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
