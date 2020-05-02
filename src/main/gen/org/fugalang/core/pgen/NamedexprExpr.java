package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * namedexpr_expr: 'NAME' [':=' 'expr']
 */
public final class NamedexprExpr extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("namedexpr_expr", RuleType.Conjunction, true);

    public static NamedexprExpr of(ParseTreeNode node) {
        return new NamedexprExpr(node);
    }

    private NamedexprExpr(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired("name", name());
        addOptional("namedexprExpr2", namedexprExpr2());
    }

    public String name() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return element.asString();
    }

    public NamedexprExpr2 namedexprExpr2() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return NamedexprExpr2.of(element);
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
    public static final class NamedexprExpr2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("namedexpr_expr:2", RuleType.Conjunction, false);

        public static NamedexprExpr2 of(ParseTreeNode node) {
            return new NamedexprExpr2(node);
        }

        private NamedexprExpr2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenAsgnExpr", isTokenAsgnExpr());
            addRequired("expr", expr());
        }

        public boolean isTokenAsgnExpr() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public Expr expr() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return Expr.of(element);
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