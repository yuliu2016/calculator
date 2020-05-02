package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * namedexpr_expr: 'NAME' [':=' 'expr']
 */
public final class NamedexprExpr extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("namedexpr_expr", RuleType.Conjunction, true);

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
        addRequired("name", name());
        addOptional("namedexprExpr2", namedexprExpr2());
    }

    public String name() {
        return name;
    }

    public NamedexprExpr2 namedexprExpr2() {
        return namedexprExpr2;
    }

    public boolean hasNamedexprExpr2() {
        return namedexprExpr2() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenType("NAME");
        NamedexprExpr2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ':=' 'expr'
     */
    public static final class NamedexprExpr2 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("namedexpr_expr:2", RuleType.Conjunction, false);

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
            addRequired("isTokenAsgnExpr", isTokenAsgnExpr());
            addRequired("expr", expr());
        }

        public boolean isTokenAsgnExpr() {
            return isTokenAsgnExpr;
        }

        public Expr expr() {
            return expr;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral(":=");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
