package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.DisjunctionRule;

// expr: 'if' 'disjunction' '?' 'disjunction' 'else' 'expr' | 'disjunction' | 'funcdef'
public final class Expr extends DisjunctionRule {
    private final Expr1 expr1;
    private final Disjunction disjunction;
    private final Funcdef funcdef;

    public Expr(
            Expr1 expr1,
            Disjunction disjunction,
            Funcdef funcdef
    ) {
        this.expr1 = expr1;
        this.disjunction = disjunction;
        this.funcdef = funcdef;

        addChoice("expr1", expr1);
        addChoice("disjunction", disjunction);
        addChoice("funcdef", funcdef);
    }

    public Expr1 getExpr1() {
        return expr1;
    }

    public Disjunction getDisjunction() {
        return disjunction;
    }

    public Funcdef getFuncdef() {
        return funcdef;
    }

    // 'if' 'disjunction' '?' 'disjunction' 'else' 'expr'
    public static final class Expr1 extends ConjunctionRule {
        private final boolean isTokenIf;
        private final Disjunction disjunction;
        private final boolean isTokenTernery;
        private final Disjunction disjunction1;
        private final boolean isTokenElse;
        private final Expr expr;

        public Expr1(
                boolean isTokenIf,
                Disjunction disjunction,
                boolean isTokenTernery,
                Disjunction disjunction1,
                boolean isTokenElse,
                Expr expr
        ) {
            this.isTokenIf = isTokenIf;
            this.disjunction = disjunction;
            this.isTokenTernery = isTokenTernery;
            this.disjunction1 = disjunction1;
            this.isTokenElse = isTokenElse;
            this.expr = expr;

            addRequired("isTokenIf", isTokenIf);
            addRequired("disjunction", disjunction);
            addRequired("isTokenTernery", isTokenTernery);
            addRequired("disjunction1", disjunction1);
            addRequired("isTokenElse", isTokenElse);
            addRequired("expr", expr);
        }

        public boolean getIsTokenIf() {
            return isTokenIf;
        }

        public Disjunction getDisjunction() {
            return disjunction;
        }

        public boolean getIsTokenTernery() {
            return isTokenTernery;
        }

        public Disjunction getDisjunction1() {
            return disjunction1;
        }

        public boolean getIsTokenElse() {
            return isTokenElse;
        }

        public Expr getExpr() {
            return expr;
        }
    }
}
