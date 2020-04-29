package org.fugalang.core.pgen;

// 'NAME' '=' 'expr'
public class Argument3 {
    public final Object name;
    public final boolean isTokenAssign;
    public final Expr expr;

    public Argument3(
            Object name,
            boolean isTokenAssign,
            Expr expr
    ) {
        this.name = name;
        this.isTokenAssign = isTokenAssign;
        this.expr = expr;
    }
}
