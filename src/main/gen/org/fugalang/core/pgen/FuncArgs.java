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

    public SimpleArgList simpleArgList() {
        return SimpleArgList.of(getItem(0));
    }

    public boolean hasSimpleArgList() {
        return hasItemOfRule(0, SimpleArgList.RULE);
    }

    public FuncArgs2 funcArgs2() {
        return FuncArgs2.of(getItem(1));
    }

    public boolean hasFuncArgs2() {
        return hasItemOfRule(1, FuncArgs2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = SimpleArgList.parse(parseTree, level + 1);
        result = result || FuncArgs2.parse(parseTree, level + 1);

        parseTree.exit(result);
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

        public TypedArgList typedArgList() {
            return TypedArgList.of(getItem(1));
        }

        public boolean hasTypedArgList() {
            return hasItemOfRule(1, TypedArgList.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("(");
            if (result) TypedArgList.parse(parseTree, level + 1);
            result = result && parseTree.consumeToken(")");

            parseTree.exit(result);
            return result;
        }
    }
}
