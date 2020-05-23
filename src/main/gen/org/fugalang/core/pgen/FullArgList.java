package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

import java.util.List;

/**
 * full_arg_list: 'default_arg' (',' 'default_arg')* [',' ['kwargs' | 'args_kwargs']]
 */
public final class FullArgList extends NodeWrapper {

    public FullArgList(ParseTreeNode node) {
        super(ParserRules.FULL_ARG_LIST, node);
    }

    public DefaultArg defaultArg() {
        return get(0, DefaultArg::new);
    }

    public List<FullArgList2> defaultArgs() {
        return getList(1, FullArgList2::new);
    }

    public FullArgList3 fullArgList3() {
        return get(2, FullArgList3::new);
    }

    public boolean hasFullArgList3() {
        return has(2);
    }

    /**
     * ',' 'default_arg'
     */
    public static final class FullArgList2 extends NodeWrapper {

        public FullArgList2(ParseTreeNode node) {
            super(ParserRules.FULL_ARG_LIST_2, node);
        }

        public DefaultArg defaultArg() {
            return get(1, DefaultArg::new);
        }
    }

    /**
     * ',' ['kwargs' | 'args_kwargs']
     */
    public static final class FullArgList3 extends NodeWrapper {

        public FullArgList3(ParseTreeNode node) {
            super(ParserRules.FULL_ARG_LIST_3, node);
        }

        public FullArgList32 kwargsOrArgsKwargs() {
            return get(1, FullArgList32::new);
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
            super(ParserRules.FULL_ARG_LIST_3_2, node);
        }

        public Kwargs kwargs() {
            return get(0, Kwargs::new);
        }

        public boolean hasKwargs() {
            return has(0);
        }

        public ArgsKwargs argsKwargs() {
            return get(1, ArgsKwargs::new);
        }

        public boolean hasArgsKwargs() {
            return has(1);
        }
    }
}
