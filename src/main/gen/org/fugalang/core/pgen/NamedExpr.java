package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * named_expr: 'NAME' ':=' 'expr' | 'expr'
 */
public final class NamedExpr extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("named_expr", RuleType.Disjunction, true);

    public static NamedExpr of(ParseTreeNode node) {
        return new NamedExpr(node);
    }

    private NamedExpr(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addChoice(namedExpr1OrNull());
        addChoice(exprOrNull());
    }

    public NamedExpr1 namedExpr1() {
        var element = getItem(0);
        element.failIfAbsent(NamedExpr1.RULE);
        return NamedExpr1.of(element);
    }

    public NamedExpr1 namedExpr1OrNull() {
        var element = getItem(0);
        if (!element.isPresent(NamedExpr1.RULE)) {
            return null;
        }
        return NamedExpr1.of(element);
    }

    public boolean hasNamedExpr1() {
        var element = getItem(0);
        return element.isPresent(NamedExpr1.RULE);
    }

    public Expr expr() {
        var element = getItem(1);
        element.failIfAbsent(Expr.RULE);
        return Expr.of(element);
    }

    public Expr exprOrNull() {
        var element = getItem(1);
        if (!element.isPresent(Expr.RULE)) {
            return null;
        }
        return Expr.of(element);
    }

    public boolean hasExpr() {
        var element = getItem(1);
        return element.isPresent(Expr.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = NamedExpr1.parse(parseTree, level + 1);
        result = result || Expr.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'NAME' ':=' 'expr'
     */
    public static final class NamedExpr1 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("named_expr:1", RuleType.Conjunction, false);

        public static NamedExpr1 of(ParseTreeNode node) {
            return new NamedExpr1(node);
        }

        private NamedExpr1(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(name());
            addRequired(isTokenAsgnExpr(), ":=");
            addRequired(expr());
        }

        public String name() {
            var element = getItem(0);
            element.failIfAbsent(TokenType.NAME);
            return element.asString();
        }

        public boolean isTokenAsgnExpr() {
            var element = getItem(1);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Expr expr() {
            var element = getItem(2);
            element.failIfAbsent(Expr.RULE);
            return Expr.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(TokenType.NAME);
            result = result && parseTree.consumeToken(":=");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
