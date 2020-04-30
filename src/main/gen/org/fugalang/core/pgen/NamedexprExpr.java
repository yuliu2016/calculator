package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// namedexpr_expr: 'NAME' [':=' 'expr']
public final class NamedexprExpr extends ConjunctionRule {
    private final String name;
    private final NamedexprExpr2Group namedexprExpr2Group;

    public NamedexprExpr(
            String name,
            NamedexprExpr2Group namedexprExpr2Group
    ) {
        this.name = name;
        this.namedexprExpr2Group = namedexprExpr2Group;

        addRequired("name", name);
        addOptional("namedexprExpr2Group", namedexprExpr2Group);
    }

    public String name() {
        return name;
    }

    public Optional<NamedexprExpr2Group> namedexprExpr2Group() {
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
