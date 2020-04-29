package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// namedexpr_expr: 'NAME' [':=' 'expr']
public final class NamedexprExpr extends ConjunctionRule {
    private final Object name;
    private final NamedexprExpr2Group namedexprExpr2Group;

    public NamedexprExpr(
            Object name,
            NamedexprExpr2Group namedexprExpr2Group
    ) {
        this.name = name;
        this.namedexprExpr2Group = namedexprExpr2Group;
    }

    public Object getName() {
        return name;
    }

    public Optional<NamedexprExpr2Group> getNamedexprExpr2Group() {
        return Optional.ofNullable(namedexprExpr2Group);
    }

    // ':=' 'expr'
    public static final class NamedexprExpr2Group extends ConjunctionRule {
        private final boolean isTokenAsgnExpr;
        private final Expr expr;

        public NamedexprExpr2Group(
                boolean isTokenAsgnExpr,
                Expr expr
        ) {
            this.isTokenAsgnExpr = isTokenAsgnExpr;
            this.expr = expr;
        }

        public boolean getIsTokenAsgnExpr() {
            return isTokenAsgnExpr;
        }

        public Expr getExpr() {
            return expr;
        }
    }
}
