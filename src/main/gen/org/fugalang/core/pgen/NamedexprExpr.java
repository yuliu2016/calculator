package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// namedexpr_expr: 'NAME' [':=' 'expr']
public final class NamedexprExpr extends ConjunctionRule {
    private final String name;
    private final NamedexprExpr2 namedexprExpr2;

    public NamedexprExpr(
            String name,
            NamedexprExpr2 namedexprExpr2
    ) {
        this.name = name;
        this.namedexprExpr2 = namedexprExpr2;
    }

    @Override
    protected void buildRule() {
        addRequired("name", name);
        addOptional("namedexprExpr2", namedexprExpr2);
    }

    public String name() {
        return name;
    }

    public Optional<NamedexprExpr2> namedexprExpr2() {
        return Optional.ofNullable(namedexprExpr2);
    }

    // ':=' 'expr'
    public static final class NamedexprExpr2 extends ConjunctionRule {
        private final boolean isTokenAsgnExpr;
        private final Expr expr;

        public NamedexprExpr2(
                boolean isTokenAsgnExpr,
                Expr expr
        ) {
            this.isTokenAsgnExpr = isTokenAsgnExpr;
            this.expr = expr;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenAsgnExpr", isTokenAsgnExpr);
            addRequired("expr", expr);
        }

        public boolean isTokenAsgnExpr() {
            return isTokenAsgnExpr;
        }

        public Expr expr() {
            return expr;
        }
    }
}
