package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * args_kwargs: '*' ['typed_arg'] (',' 'default_arg')* [',' ['kwargs']]
 */
public final class ArgsKwargs extends NodeWrapper {

    public ArgsKwargs(ParseTreeNode node) {
        super(node);
    }

    public TypedArg typedArg() {
        return get(1, TypedArg.class);
    }

    public boolean hasTypedArg() {
        return has(1);
    }

    public List<ArgsKwargs3> defaultArgs() {
        return getList(2, ArgsKwargs3.class);
    }

    public ArgsKwargs4 argsKwargs4() {
        return get(3, ArgsKwargs4.class);
    }

    public boolean hasArgsKwargs4() {
        return has(3);
    }

    /**
     * ',' 'default_arg'
     */
    public static final class ArgsKwargs3 extends NodeWrapper {

        public ArgsKwargs3(ParseTreeNode node) {
            super(node);
        }

        public DefaultArg defaultArg() {
            return get(1, DefaultArg.class);
        }
    }

    /**
     * ',' ['kwargs']
     */
    public static final class ArgsKwargs4 extends NodeWrapper {

        public ArgsKwargs4(ParseTreeNode node) {
            super(node);
        }

        public Kwargs kwargs() {
            return get(1, Kwargs.class);
        }

        public boolean hasKwargs() {
            return has(1);
        }
    }
}
