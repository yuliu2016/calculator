package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * argument: 'NAME' ['comp_for'] | 'NAME' ':=' 'expr' | 'NAME' '=' 'expr' | '**' 'expr' | '*' 'expr'
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
        addChoice(argument1());
        addChoice(argument2());
        addChoice(argument3());
        addChoice(argument4());
        addChoice(argument5());
    }

    public Argument1 argument1() {
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
        if (!element.isPresent(Argument4.RULE)) {
            return null;
        }
        return Argument4.of(element);
    }

    public boolean hasArgument4() {
        var element = getItem(3);
        return element.isPresent(Argument4.RULE);
    }

    public Argument5 argument5() {
        var element = getItem(4);
        if (!element.isPresent(Argument5.RULE)) {
            return null;
        }
        return Argument5.of(element);
    }

    public boolean hasArgument5() {
        var element = getItem(4);
        return element.isPresent(Argument5.RULE);
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
        result = result || Argument5.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'NAME' ['comp_for']
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
            addOptional(compFor());
        }

        public String name() {
            var element = getItem(0);
            element.failIfAbsent(TokenType.NAME);
            return element.asString();
        }

        public CompFor compFor() {
            var element = getItem(1);
            if (!element.isPresent(CompFor.RULE)) {
                return null;
            }
            return CompFor.of(element);
        }

        public boolean hasCompFor() {
            var element = getItem(1);
            return element.isPresent(CompFor.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(TokenType.NAME);
            if (result) CompFor.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * 'NAME' ':=' 'expr'
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
    public static final class Argument5 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("argument:5", RuleType.Conjunction, false);

        public static Argument5 of(ParseTreeNode node) {
            return new Argument5(node);
        }

        private Argument5(ParseTreeNode node) {
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
