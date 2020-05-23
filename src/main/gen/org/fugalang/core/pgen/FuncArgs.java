package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * func_args: 'simple_arg_list' | '(' ['typed_arg_list'] ')'
 */
public final class FuncArgs extends NodeWrapper {

    public FuncArgs(ParseTreeNode node) {
        super(ParserRules.FUNC_ARGS, node);
    }

    public SimpleArgList simpleArgList() {
        return get(0, SimpleArgList::new);
    }

    public boolean hasSimpleArgList() {
        return has(0, ParserRules.SIMPLE_ARG_LIST);
    }

    public FuncArgs2 funcArgs2() {
        return get(1, FuncArgs2::new);
    }

    public boolean hasFuncArgs2() {
        return has(1, ParserRules.FUNC_ARGS_2);
    }

    /**
     * '(' ['typed_arg_list'] ')'
     */
    public static final class FuncArgs2 extends NodeWrapper {

        public FuncArgs2(ParseTreeNode node) {
            super(ParserRules.FUNC_ARGS_2, node);
        }

        public TypedArgList typedArgList() {
            return get(1, TypedArgList::new);
        }

        public boolean hasTypedArgList() {
            return has(1, ParserRules.TYPED_ARG_LIST);
        }
    }
}
