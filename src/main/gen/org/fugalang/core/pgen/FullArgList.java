package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * full_arg_list: 'default_arg' (',' 'default_arg')* [',' ['kwargs' | 'args_kwargs']]
 */
public final class FullArgList extends NodeWrapper {

    public FullArgList(ParseTreeNode node) {
        super(node);
    }

    public DefaultArg defaultArg() {
        return get(0, DefaultArg.class);
    }

    public List<FullArgList2> defaultArgs() {
        return getList(1, FullArgList2.class);
    }

    public FullArgList3 fullArgList3() {
        return get(2, FullArgList3.class);
    }

    public boolean hasFullArgList3() {
        return has(2);
    }

    /**
     * ',' 'default_arg'
     */
    public static final class FullArgList2 extends NodeWrapper {

        public FullArgList2(ParseTreeNode node) {
            super(node);
        }

        public DefaultArg defaultArg() {
            return get(1, DefaultArg.class);
        }
    }

    /**
     * ',' ['kwargs' | 'args_kwargs']
     */
    public static final class FullArgList3 extends NodeWrapper {

        public FullArgList3(ParseTreeNode node) {
            super(node);
        }

        public FullArgList32 kwargsOrArgsKwargs() {
            return get(1, FullArgList32.class);
        }

        public boolean hasKwargsOrArgsKwargs() {
            return has(1);
        }
    }

    /**
     * 'kwargs' | 'args_kwargs'
     */
    public static final class FullArgList32 extends NodeWrapper {

        public FullArgList32(ParseTreeNode node) {
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
