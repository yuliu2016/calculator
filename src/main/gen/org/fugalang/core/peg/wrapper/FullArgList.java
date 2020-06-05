package org.fugalang.core.peg.wrapper;

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
        return getList(0, DefaultArg::new);
    }

    public FullArgList2 fullArgList2() {
        return new FullArgList2(get(1));
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
            return new FullArgList22(get(1));
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
            return new Kwargs(get(0));
        }

        public boolean hasKwargs() {
            return has(0);
        }

        public ArgsKwargs argsKwargs() {
            return new ArgsKwargs(get(1));
        }

        public boolean hasArgsKwargs() {
            return has(1);
        }
    }
}
