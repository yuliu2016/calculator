package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// namedexpr_expr: 'NAME' [':=' 'expr']
public final class NamedexprExpr extends ConjunctionRule {
    public static final String RULE_NAME = "namedexpr_expr";

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
        setExplicitName(RULE_NAME);
        addRequired("name", name);
        addOptional("namedexprExpr2", namedexprExpr2);
    }

    public String name() {
        return name;
    }

    public Optional<NamedexprExpr2> namedexprExpr2() {
        return Optional.ofNullable(namedexprExpr2);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = parseTree.consumeTokenType("NAME");
        NamedexprExpr2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    // ':=' 'expr'
    public static final class NamedexprExpr2 extends ConjunctionRule {
        public static final String RULE_NAME = "namedexpr_expr:2";

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
            setImpliedName(RULE_NAME);
            addRequired("isTokenAsgnExpr", isTokenAsgnExpr);
            addRequired("expr", expr);
        }

        public boolean isTokenAsgnExpr() {
            return isTokenAsgnExpr;
        }

        public Expr expr() {
            return expr;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral(":=");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
