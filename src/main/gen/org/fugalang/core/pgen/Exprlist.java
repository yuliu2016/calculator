package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// exprlist: 'expr' (',' 'expr')* [',']
public final class Exprlist extends ConjunctionRule {
    private final Expr expr;
    private final List<Exprlist2Group> exprlist2GroupList;
    private final boolean isTokenComma;

    public Exprlist(
            Expr expr,
            List<Exprlist2Group> exprlist2GroupList,
            boolean isTokenComma
    ) {
        this.expr = expr;
        this.exprlist2GroupList = exprlist2GroupList;
        this.isTokenComma = isTokenComma;

        addRequired("expr", expr);
        addRequired("exprlist2GroupList", exprlist2GroupList);
        addRequired("isTokenComma", isTokenComma);
    }

    public Expr expr() {
        return expr;
    }

    public List<Exprlist2Group> exprlist2GroupList() {
        return exprlist2GroupList;
    }

    public boolean isTokenComma() {
        return isTokenComma;
    }

    // ',' 'expr'
    public static final class Exprlist2Group extends ConjunctionRule {
        private final boolean isTokenComma;
        private final Expr expr;

        public Exprlist2Group(
                boolean isTokenComma,
                Expr expr
        ) {
            this.isTokenComma = isTokenComma;
            this.expr = expr;

            addRequired("isTokenComma", isTokenComma);
            addRequired("expr", expr);
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public Expr expr() {
            return expr;
        }
    }
}
