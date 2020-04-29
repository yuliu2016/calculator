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
}
