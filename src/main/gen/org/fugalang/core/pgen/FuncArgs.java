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
        return get(0, SimpleArgList.class);
    }

    public boolean hasSimpleArgList() {
        return has(0);
    }

    public FuncArgs2 funcArgs2() {
        return get(1, FuncArgs2.class);
    }

    public boolean hasFuncArgs2() {
        return has(1);
    }

    /**
     * '(' ['typed_arg_list'] ')'
     */
    public static final class FuncArgs2 extends NodeWrapper {

        public FuncArgs2(ParseTreeNode node) {
            super(ParserRules.FUNC_ARGS_2, node);
        }

        public TypedArgList typedArgList() {
            return get(1, TypedArgList.class);
        }

        public boolean hasTypedArgList() {
            return has(1);
        }
    }
}
