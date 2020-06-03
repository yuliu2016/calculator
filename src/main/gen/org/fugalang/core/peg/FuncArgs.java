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
        return getList(0, SimpleArg::new);
    }

    public FuncArgs2 funcArgs2() {
        return new FuncArgs2(get(1));
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
            return new TypedArgList(get(1));
        }

        public boolean hasTypedArgList() {
            return has(1);
        }
    }
}
