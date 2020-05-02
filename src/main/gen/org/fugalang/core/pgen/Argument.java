package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

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
        addChoice("argument1", argument1());
        addChoice("argument2", argument2());
        addChoice("argument3", argument3());
        addChoice("argument4", argument4());
        addChoice("argument5", argument5());
    }

    public Argument1 argument1() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return Argument1.of(element);
    }

    public boolean hasArgument1() {
        return argument1() != null;
    }

    public Argument2 argument2() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return Argument2.of(element);
    }

    public boolean hasArgument2() {
        return argument2() != null;
    }

    public Argument3 argument3() {
        var element = getItem(2);
        if (!element.isPresent()) return null;
        return Argument3.of(element);
    }

    public boolean hasArgument3() {
        return argument3() != null;
    }

    public Argument4 argument4() {
        var element = getItem(3);
        if (!element.isPresent()) return null;
        return Argument4.of(element);
    }

    public boolean hasArgument4() {
        return argument4() != null;
    }

    public Argument5 argument5() {
        var element = getItem(4);
        if (!element.isPresent()) return null;
        return Argument5.of(element);
    }

    public boolean hasArgument5() {
        return argument5() != null;
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
            addRequired("name", name());
            addOptional("compFor", compFor());
        }

        public String name() {
            var element = getItem(0);
            if (!element.isPresent()) return null;
            return element.asString();
        }

        public CompFor compFor() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return CompFor.of(element);
        }

        public boolean hasCompFor() {
            return compFor() != null;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenType("NAME");
            CompFor.parse(parseTree, level + 1);

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
            addRequired("name", name());
            addRequired("isTokenAsgnExpr", isTokenAsgnExpr());
            addRequired("expr", expr());
        }

        public String name() {
            var element = getItem(0);
            if (!element.isPresent()) return null;
            return element.asString();
        }

        public boolean isTokenAsgnExpr() {
            var element = getItem(1);
            return element.asBoolean();
        }

        public Expr expr() {
            var element = getItem(2);
            if (!element.isPresent()) return null;
            return Expr.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenType("NAME");
            result = result && parseTree.consumeTokenLiteral(":=");
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
            addRequired("name", name());
            addRequired("isTokenAssign", isTokenAssign());
            addRequired("expr", expr());
        }

        public String name() {
            var element = getItem(0);
            if (!element.isPresent()) return null;
            return element.asString();
        }

        public boolean isTokenAssign() {
            var element = getItem(1);
            return element.asBoolean();
        }

        public Expr expr() {
            var element = getItem(2);
            if (!element.isPresent()) return null;
            return Expr.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenType("NAME");
            result = result && parseTree.consumeTokenLiteral("=");
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
            addRequired("isTokenPower", isTokenPower());
            addRequired("expr", expr());
        }

        public boolean isTokenPower() {
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

            result = parseTree.consumeTokenLiteral("**");
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
            addRequired("isTokenTimes", isTokenTimes());
            addRequired("expr", expr());
        }

        public boolean isTokenTimes() {
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

            result = parseTree.consumeTokenLiteral("*");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
