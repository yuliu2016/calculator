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

    public Expr1 expr1() {
        return Expr1.of(getItem(0));
    }

    public boolean hasExpr1() {
        return hasItemOfRule(0, Expr1.RULE);
    }

    public Funcdef funcdef() {
        return Funcdef.of(getItem(1));
    }

    public boolean hasFuncdef() {
        return hasItemOfRule(1, Funcdef.RULE);
    }

    public Disjunction disjunction() {
        return Disjunction.of(getItem(2));
    }

    public boolean hasDisjunction() {
        return hasItemOfRule(2, Disjunction.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
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

        public boolean isTokenIf() {
            return true;
        }

        public Disjunction disjunction() {
            return Disjunction.of(getItem(1));
        }

        public boolean isTokenTernery() {
            return true;
        }

        public Disjunction disjunction1() {
            return Disjunction.of(getItem(3));
        }

        public boolean isTokenElse() {
            return true;
        }

        public Expr expr() {
            return Expr.of(getItem(5));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
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
