package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * func_args: simple_arg+ | '(' [typed_arg_list] ')'
 */
public final class FuncArgs extends NodeWrapper {

    public FuncArgs(ParseTreeNode node) {
        super(node);
    }

    public List<SimpleArg> simpleArgs() {
        return getList(0, SimpleArg.class);
    }

    public FuncArgs2 funcArgs2() {
        return get(1, FuncArgs2.class);
    }

    public boolean hasFuncArgs2() {
        return has(1);
    }

    /**
     * '(' [typed_arg_list] ')'
     */
    public static final class FuncArgs2 extends NodeWrapper {

        public FuncArgs2(ParseTreeNode node) {
            super(node);
        }

        public TypedArgList typedArgList() {
            return get(1, TypedArgList.class);
        }

        public boolean hasTypedArgList() {
            return has(1);
        }
    }
}
