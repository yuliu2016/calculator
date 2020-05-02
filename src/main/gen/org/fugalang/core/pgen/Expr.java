package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * expr: 'if' 'disjunction' '?' 'disjunction' 'else' 'expr' | 'disjunction' | 'funcdef'
 */
public final class Expr extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("expr", RuleType.Disjunction, true);

    public static Expr of(ParseTreeNode node) {
        return new Expr(node);
    }

    private Expr(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addChoice("expr1", expr1());
        addChoice("disjunction", disjunction());
        addChoice("funcdef", funcdef());
    }

    public Expr1 expr1() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return Expr1.of(element);
    }

    public boolean hasExpr1() {
        return expr1() != null;
    }

    public Disjunction disjunction() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return Disjunction.of(element);
    }

    public boolean hasDisjunction() {
        return disjunction() != null;
    }

    public Funcdef funcdef() {
        var element = getItem(2);
        if (!element.isPresent()) return null;
        return Funcdef.of(element);
    }

    public boolean hasFuncdef() {
        return funcdef() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Expr1.parse(parseTree, level + 1);
        result = result || Disjunction.parse(parseTree, level + 1);
        result = result || Funcdef.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'if' 'disjunction' '?' 'disjunction' 'else' 'expr'
     */
    public static final class Expr1 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("expr:1", RuleType.Conjunction, false);

        public static Expr1 of(ParseTreeNode node) {
            return new Expr1(node);
        }

        private Expr1(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenIf", isTokenIf());
            addRequired("disjunction", disjunction());
            addRequired("isTokenTernery", isTokenTernery());
            addRequired("disjunction1", disjunction1());
            addRequired("isTokenElse", isTokenElse());
            addRequired("expr", expr());
        }

        public boolean isTokenIf() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public Disjunction disjunction() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return Disjunction.of(element);
        }

        public boolean isTokenTernery() {
            var element = getItem(2);
            return element.asBoolean();
        }

        public Disjunction disjunction1() {
            var element = getItem(3);
            if (!element.isPresent()) return null;
            return Disjunction.of(element);
        }

        public boolean isTokenElse() {
            var element = getItem(4);
            return element.asBoolean();
        }

        public Expr expr() {
            var element = getItem(5);
            if (!element.isPresent()) return null;
            return Expr.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("if");
            result = result && Disjunction.parse(parseTree, level + 1);
            result = result && parseTree.consumeTokenLiteral("?");
            result = result && Disjunction.parse(parseTree, level + 1);
            result = result && parseTree.consumeTokenLiteral("else");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
