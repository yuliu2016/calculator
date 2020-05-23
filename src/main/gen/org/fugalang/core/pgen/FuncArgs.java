package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * func_args: 'simple_arg_list' | '(' ['typed_arg_list'] ')'
 */
public final class FuncArgs extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("func_args", RuleType.Disjunction);

    public static FuncArgs of(ParseTreeNode node) {
        return new FuncArgs(node);
    }

    private FuncArgs(ParseTreeNode node) {
        super(RULE, node);
    }

    public SimpleArgList simpleArgList() {
        return get(0, SimpleArgList::of);
    }

    public boolean hasSimpleArgList() {
        return has(0, SimpleArgList.RULE);
    }

    public FuncArgs2 funcArgs2() {
        return get(1, FuncArgs2::of);
    }

    public boolean hasFuncArgs2() {
        return has(1, FuncArgs2.RULE);
    }

    /**
     * '(' ['typed_arg_list'] ')'
     */
    public static final class FuncArgs2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("func_args:2", RuleType.Conjunction);

        public static FuncArgs2 of(ParseTreeNode node) {
            return new FuncArgs2(node);
        }

        private FuncArgs2(ParseTreeNode node) {
            super(RULE, node);
        }

        public TypedArgList typedArgList() {
            return get(1, TypedArgList::of);
        }

        public boolean hasTypedArgList() {
            return has(1, TypedArgList.RULE);
        }
    }
}
