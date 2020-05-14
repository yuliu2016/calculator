package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * argument: 'NAME' ':=' 'expr' | 'NAME' '=' 'expr' | '**' 'expr' | '*' 'expr' | 'expr'
 */
public final class Argument extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("argument", RuleType.Disjunction, true);

    public static Argument of(ParseTreeNode node) {
        return new Argument(node);
    }

    private Argument(ParseTreeNode node) {
        super(RULE, node);
    }

    public Argument1 argument1() {
        return Argument1.of(getItem(0));
    }

    public boolean hasArgument1() {
        return hasItemOfRule(0, Argument1.RULE);
    }

    public Argument2 argument2() {
        return Argument2.of(getItem(1));
    }

    public boolean hasArgument2() {
        return hasItemOfRule(1, Argument2.RULE);
    }

    public Argument3 argument3() {
        return Argument3.of(getItem(2));
    }

    public boolean hasArgument3() {
        return hasItemOfRule(2, Argument3.RULE);
    }

    public Argument4 argument4() {
        return Argument4.of(getItem(3));
    }

    public boolean hasArgument4() {
        return hasItemOfRule(3, Argument4.RULE);
    }

    public Expr expr() {
        return Expr.of(getItem(4));
    }

    public boolean hasExpr() {
        return hasItemOfRule(4, Expr.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = Argument1.parse(parseTree, level + 1);
        result = result || Argument2.parse(parseTree, level + 1);
        result = result || Argument3.parse(parseTree, level + 1);
        result = result || Argument4.parse(parseTree, level + 1);
        result = result || Expr.parse(parseTree, level + 1);

        parseTree.exit(result);
        return result;
    }

    /**
     * 'NAME' ':=' 'expr'
     */
    public static final class Argument1 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("argument:1", RuleType.Conjunction, false);

        public static Argument1 of(ParseTreeNode node) {
            return new Argument1(node);
        }

        private Argument1(ParseTreeNode node) {
            super(RULE, node);
        }

        public String name() {
            return getItemOfType(0, TokenType.NAME);
        }

        public Expr expr() {
            return Expr.of(getItem(2));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(TokenType.NAME);
            result = result && parseTree.consumeToken(":=");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(result);
            return result;
        }
    }

    /**
     * 'NAME' '=' 'expr'
     */
    public static final class Argument2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("argument:2", RuleType.Conjunction, false);

        public static Argument2 of(ParseTreeNode node) {
            return new Argument2(node);
        }

        private Argument2(ParseTreeNode node) {
            super(RULE, node);
        }

        public String name() {
            return getItemOfType(0, TokenType.NAME);
        }

        public Expr expr() {
            return Expr.of(getItem(2));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(TokenType.NAME);
            result = result && parseTree.consumeToken("=");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(result);
            return result;
        }
    }

    /**
     * '**' 'expr'
     */
    public static final class Argument3 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("argument:3", RuleType.Conjunction, false);

        public static Argument3 of(ParseTreeNode node) {
            return new Argument3(node);
        }

        private Argument3(ParseTreeNode node) {
            super(RULE, node);
        }

        public Expr expr() {
            return Expr.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("**");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(result);
            return result;
        }
    }

    /**
     * '*' 'expr'
     */
    public static final class Argument4 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("argument:4", RuleType.Conjunction, false);

        public static Argument4 of(ParseTreeNode node) {
            return new Argument4(node);
        }

        private Argument4(ParseTreeNode node) {
            super(RULE, node);
        }

        public Expr expr() {
            return Expr.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("*");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(result);
            return result;
        }
    }
}
