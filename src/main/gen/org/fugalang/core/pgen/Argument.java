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

    @Override
    protected void buildRule() {
        addChoice(argument1OrNull());
        addChoice(argument2OrNull());
        addChoice(argument3OrNull());
        addChoice(argument4OrNull());
        addChoice(exprOrNull());
    }

    public Argument1 argument1() {
        var element = getItem(0);
        element.failIfAbsent(Argument1.RULE);
        return Argument1.of(element);
    }

    public Argument1 argument1OrNull() {
        var element = getItem(0);
        if (!element.isPresent(Argument1.RULE)) {
            return null;
        }
        return Argument1.of(element);
    }

    public boolean hasArgument1() {
        var element = getItem(0);
        return element.isPresent(Argument1.RULE);
    }

    public Argument2 argument2() {
        var element = getItem(1);
        element.failIfAbsent(Argument2.RULE);
        return Argument2.of(element);
    }

    public Argument2 argument2OrNull() {
        var element = getItem(1);
        if (!element.isPresent(Argument2.RULE)) {
            return null;
        }
        return Argument2.of(element);
    }

    public boolean hasArgument2() {
        var element = getItem(1);
        return element.isPresent(Argument2.RULE);
    }

    public Argument3 argument3() {
        var element = getItem(2);
        element.failIfAbsent(Argument3.RULE);
        return Argument3.of(element);
    }

    public Argument3 argument3OrNull() {
        var element = getItem(2);
        if (!element.isPresent(Argument3.RULE)) {
            return null;
        }
        return Argument3.of(element);
    }

    public boolean hasArgument3() {
        var element = getItem(2);
        return element.isPresent(Argument3.RULE);
    }

    public Argument4 argument4() {
        var element = getItem(3);
        element.failIfAbsent(Argument4.RULE);
        return Argument4.of(element);
    }

    public Argument4 argument4OrNull() {
        var element = getItem(3);
        if (!element.isPresent(Argument4.RULE)) {
            return null;
        }
        return Argument4.of(element);
    }

    public boolean hasArgument4() {
        var element = getItem(3);
        return element.isPresent(Argument4.RULE);
    }

    public Expr expr() {
        var element = getItem(4);
        element.failIfAbsent(Expr.RULE);
        return Expr.of(element);
    }

    public Expr exprOrNull() {
        var element = getItem(4);
        if (!element.isPresent(Expr.RULE)) {
            return null;
        }
        return Expr.of(element);
    }

    public boolean hasExpr() {
        var element = getItem(4);
        return element.isPresent(Expr.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Argument1.parse(parseTree, level + 1);
        result = result || Argument2.parse(parseTree, level + 1);
        result = result || Argument3.parse(parseTree, level + 1);
        result = result || Argument4.parse(parseTree, level + 1);
        result = result || Expr.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
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

        @Override
        protected void buildRule() {
            addRequired(name());
            addRequired(isTokenAssign(), "=");
            addRequired(expr());
        }

        public String name() {
            var element = getItem(0);
            element.failIfAbsent(TokenType.NAME);
            return element.asString();
        }

        public boolean isTokenAssign() {
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
            result = result && parseTree.consumeToken("=");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
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

        @Override
        protected void buildRule() {
            addRequired(isTokenPower(), "**");
            addRequired(expr());
        }

        public boolean isTokenPower() {
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

            result = parseTree.consumeToken("**");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
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

        @Override
        protected void buildRule() {
            addRequired(isTokenTimes(), "*");
            addRequired(expr());
        }

        public boolean isTokenTimes() {
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

            result = parseTree.consumeToken("*");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
