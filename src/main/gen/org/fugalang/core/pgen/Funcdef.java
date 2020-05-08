package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * funcdef: 'def' ['simple_arg_list' | '(' ['typed_arg_list'] ')'] 'func_suite'
 */
public final class Funcdef extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("funcdef", RuleType.Conjunction, true);

    public static Funcdef of(ParseTreeNode node) {
        return new Funcdef(node);
    }

    private Funcdef(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired(isTokenDef(), "def");
        addOptional(funcdef2OrNull());
        addRequired(funcSuite());
    }

    public boolean isTokenDef() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public Funcdef2 funcdef2() {
        var element = getItem(1);
        element.failIfAbsent(Funcdef2.RULE);
        return Funcdef2.of(element);
    }

    public Funcdef2 funcdef2OrNull() {
        var element = getItem(1);
        if (!element.isPresent(Funcdef2.RULE)) {
            return null;
        }
        return Funcdef2.of(element);
    }

    public boolean hasFuncdef2() {
        var element = getItem(1);
        return element.isPresent(Funcdef2.RULE);
    }

    public FuncSuite funcSuite() {
        var element = getItem(2);
        element.failIfAbsent(FuncSuite.RULE);
        return FuncSuite.of(element);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("def");
        if (result) Funcdef2.parse(parseTree, level + 1);
        result = result && FuncSuite.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'simple_arg_list' | '(' ['typed_arg_list'] ')'
     */
    public static final class Funcdef2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("funcdef:2", RuleType.Disjunction, false);

        public static Funcdef2 of(ParseTreeNode node) {
            return new Funcdef2(node);
        }

        private Funcdef2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addChoice(simpleArgListOrNull());
            addChoice(funcdef22OrNull());
        }

        public SimpleArgList simpleArgList() {
            var element = getItem(0);
            element.failIfAbsent(SimpleArgList.RULE);
            return SimpleArgList.of(element);
        }

        public SimpleArgList simpleArgListOrNull() {
            var element = getItem(0);
            if (!element.isPresent(SimpleArgList.RULE)) {
                return null;
            }
            return SimpleArgList.of(element);
        }

        public boolean hasSimpleArgList() {
            var element = getItem(0);
            return element.isPresent(SimpleArgList.RULE);
        }

        public Funcdef22 funcdef22() {
            var element = getItem(1);
            element.failIfAbsent(Funcdef22.RULE);
            return Funcdef22.of(element);
        }

        public Funcdef22 funcdef22OrNull() {
            var element = getItem(1);
            if (!element.isPresent(Funcdef22.RULE)) {
                return null;
            }
            return Funcdef22.of(element);
        }

        public boolean hasFuncdef22() {
            var element = getItem(1);
            return element.isPresent(Funcdef22.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = SimpleArgList.parse(parseTree, level + 1);
            result = result || Funcdef22.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * '(' ['typed_arg_list'] ')'
     */
    public static final class Funcdef22 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("funcdef:2:2", RuleType.Conjunction, false);

        public static Funcdef22 of(ParseTreeNode node) {
            return new Funcdef22(node);
        }

        private Funcdef22(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenLpar(), "(");
            addOptional(typedArgListOrNull());
            addRequired(isTokenRpar(), ")");
        }

        public boolean isTokenLpar() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public TypedArgList typedArgList() {
            var element = getItem(1);
            element.failIfAbsent(TypedArgList.RULE);
            return TypedArgList.of(element);
        }

        public TypedArgList typedArgListOrNull() {
            var element = getItem(1);
            if (!element.isPresent(TypedArgList.RULE)) {
                return null;
            }
            return TypedArgList.of(element);
        }

        public boolean hasTypedArgList() {
            var element = getItem(1);
            return element.isPresent(TypedArgList.RULE);
        }

        public boolean isTokenRpar() {
            var element = getItem(2);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("(");
            if (result) TypedArgList.parse(parseTree, level + 1);
            result = result && parseTree.consumeToken(")");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
