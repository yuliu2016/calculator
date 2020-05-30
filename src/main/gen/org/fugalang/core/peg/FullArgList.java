package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * full_arg_list: ','.default_arg+ [',' [kwargs | args_kwargs]]
 */
public final class FullArgList extends NodeWrapper {

    public FullArgList(ParseTreeNode node) {
        super(node);
    }

    public List<DefaultArg> defaultArgs() {
        return getList(0, DefaultArg.class);
    }

    public FullArgList2 fullArgList2() {
        return get(1, FullArgList2.class);
    }

    public boolean hasFullArgList2() {
        return has(1);
    }

    /**
     * ',' [kwargs | args_kwargs]
     */
    public static final class FullArgList2 extends NodeWrapper {

        public FullArgList2(ParseTreeNode node) {
            super(node);
        }

        public FullArgList22 kwargsOrArgsKwargs() {
            return get(1, FullArgList22.class);
        }

        public boolean hasKwargsOrArgsKwargs() {
            return has(1);
        }
    }

    /**
     * kwargs | args_kwargs
     */
    public static final class FullArgList22 extends NodeWrapper {

        public FullArgList22(ParseTreeNode node) {
            super(node);
        }

        public Kwargs kwargs() {
            return get(0, Kwargs.class);
        }

        public boolean hasKwargs() {
            return has(0);
        }

        public ArgsKwargs argsKwargs() {
            return get(1, ArgsKwargs.class);
        }

        public boolean hasArgsKwargs() {
            return has(1);
        }
    }
}
