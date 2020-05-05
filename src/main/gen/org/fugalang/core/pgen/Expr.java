package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * expr: 'if' 'disjunction' '?' 'disjunction' 'else' 'expr' | 'funcdef' | 'disjunction'
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
        addChoice(expr1());
        addChoice(funcdef());
        addChoice(disjunction());
    }

    public Expr1 expr1() {
        var element = getItem(0);
        if (!element.isPresent(Expr1.RULE)) {
            return null;
        }
        return Expr1.of(element);
    }

    public boolean hasExpr1() {
        var element = getItem(0);
        return element.isPresent(Expr1.RULE);
    }

    public Funcdef funcdef() {
        var element = getItem(1);
        if (!element.isPresent(Funcdef.RULE)) {
            return null;
        }
        return Funcdef.of(element);
    }

    public boolean hasFuncdef() {
        var element = getItem(1);
        return element.isPresent(Funcdef.RULE);
    }

    public Disjunction disjunction() {
        var element = getItem(2);
        if (!element.isPresent(Disjunction.RULE)) {
            return null;
        }
        return Disjunction.of(element);
    }

    public boolean hasDisjunction() {
        var element = getItem(2);
        return element.isPresent(Disjunction.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Expr1.parse(parseTree, level + 1);
        result = result || Funcdef.parse(parseTree, level + 1);
        result = result || Disjunction.parse(parseTree, level + 1);

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
            addRequired(isTokenIf(), "if");
            addRequired(disjunction());
            addRequired(isTokenTernery(), "?");
            addRequired(disjunction1());
            addRequired(isTokenElse(), "else");
            addRequired(expr());
        }

        public boolean isTokenIf() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Disjunction disjunction() {
            var element = getItem(1);
            element.failIfAbsent(Disjunction.RULE);
            return Disjunction.of(element);
        }

        public boolean isTokenTernery() {
            var element = getItem(2);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Disjunction disjunction1() {
            var element = getItem(3);
            element.failIfAbsent(Disjunction.RULE);
            return Disjunction.of(element);
        }

        public boolean isTokenElse() {
            var element = getItem(4);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Expr expr() {
            var element = getItem(5);
            element.failIfAbsent(Expr.RULE);
            return Expr.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("if");
            result = result && Disjunction.parse(parseTree, level + 1);
            result = result && parseTree.consumeToken("?");
            result = result && Disjunction.parse(parseTree, level + 1);
            result = result && parseTree.consumeToken("else");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
