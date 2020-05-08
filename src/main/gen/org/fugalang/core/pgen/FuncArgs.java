package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * func_args: 'simple_arg_list' | '(' ['typed_arg_list'] ')'
 */
public final class FuncArgs extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("func_args", RuleType.Disjunction, true);

    public static FuncArgs of(ParseTreeNode node) {
        return new FuncArgs(node);
    }

    private FuncArgs(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addChoice(simpleArgListOrNull());
        addChoice(funcArgs2OrNull());
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

    public FuncArgs2 funcArgs2() {
        var element = getItem(1);
        element.failIfAbsent(FuncArgs2.RULE);
        return FuncArgs2.of(element);
    }

    public FuncArgs2 funcArgs2OrNull() {
        var element = getItem(1);
        if (!element.isPresent(FuncArgs2.RULE)) {
            return null;
        }
        return FuncArgs2.of(element);
    }

    public boolean hasFuncArgs2() {
        var element = getItem(1);
        return element.isPresent(FuncArgs2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = SimpleArgList.parse(parseTree, level + 1);
        result = result || FuncArgs2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '(' ['typed_arg_list'] ')'
     */
    public static final class FuncArgs2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("func_args:2", RuleType.Conjunction, false);

        public static FuncArgs2 of(ParseTreeNode node) {
            return new FuncArgs2(node);
        }

        private FuncArgs2(ParseTreeNode node) {
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
