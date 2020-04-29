package org.fugalang.core.pgen;

// namedexpr_expr: 'NAME' [':=' 'expr']
public class NamedexprExpr {
    public final Object name;
    public final NamedexprExpr2Group namedexprExpr2Group;

    public NamedexprExpr(
            Object name,
            NamedexprExpr2Group namedexprExpr2Group
    ) {
        this.name = name;
        this.namedexprExpr2Group = namedexprExpr2Group;
    }
}
