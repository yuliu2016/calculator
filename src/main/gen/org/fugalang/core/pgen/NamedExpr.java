package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * named_expr: 'NAME' [':=' 'expr']
 */
public final class NamedExpr extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("named_expr", RuleType.Conjunction, true);

    public static NamedExpr of(ParseTreeNode node) {
        return new NamedExpr(node);
    }

    private NamedExpr(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired(name());
        addOptional(namedExpr2OrNull());
    }

    public String name() {
        var element = getItem(0);
        element.failIfAbsent(TokenType.NAME);
        return element.asString();
    }

    public NamedExpr2 namedExpr2() {
        var element = getItem(1);
        element.failIfAbsent(NamedExpr2.RULE);
        return NamedExpr2.of(element);
    }

    public NamedExpr2 namedExpr2OrNull() {
        var element = getItem(1);
        if (!element.isPresent(NamedExpr2.RULE)) {
            return null;
        }
        return NamedExpr2.of(element);
    }

    public boolean hasNamedExpr2() {
        var element = getItem(1);
        return element.isPresent(NamedExpr2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken(TokenType.NAME);
        if (result) NamedExpr2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ':=' 'expr'
     */
    public static final class NamedExpr2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("named_expr:2", RuleType.Conjunction, false);

        public static NamedExpr2 of(ParseTreeNode node) {
            return new NamedExpr2(node);
        }

        private NamedExpr2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenAsgnExpr(), ":=");
            addRequired(expr());
        }

        public boolean isTokenAsgnExpr() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Expr expr() {
            var element = getItem(1);
            element.failIfAbsent(Expr.RULE);
            return Expr.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(":=");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
