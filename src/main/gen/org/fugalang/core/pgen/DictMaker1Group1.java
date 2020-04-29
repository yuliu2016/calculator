package org.fugalang.core.pgen;

// 'expr' ':' 'expr'
public class DictMaker1Group1 {
    public final Expr expr;
    public final boolean isTokenColon;
    public final Expr expr1;

    public DictMaker1Group1(
            Expr expr,
            boolean isTokenColon,
            Expr expr1
    ) {
        this.expr = expr;
        this.isTokenColon = isTokenColon;
        this.expr1 = expr1;
    }
}
