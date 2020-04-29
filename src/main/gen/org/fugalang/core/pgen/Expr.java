package org.fugalang.core.pgen;

// expr: 'if' 'disjunction' '?' 'disjunction' 'else' 'expr' | 'disjunction' | 'funcdef'
public class Expr {
    public final Expr1 expr1;
    public final Disjunction disjunction;
    public final Funcdef funcdef;

    public Expr(
            Expr1 expr1,
            Disjunction disjunction,
            Funcdef funcdef
    ) {
        this.expr1 = expr1;
        this.disjunction = disjunction;
        this.funcdef = funcdef;
    }

    // 'if' 'disjunction' '?' 'disjunction' 'else' 'expr'
    public static class Expr1 {
        public final boolean isTokenIf;
        public final Disjunction disjunction;
        public final boolean isTokenTernery;
        public final Disjunction disjunction1;
        public final boolean isTokenElse;
        public final Expr expr;

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
        }
    }
}
