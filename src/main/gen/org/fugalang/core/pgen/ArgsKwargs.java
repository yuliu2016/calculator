package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

import java.util.List;

/**
 * args_kwargs: '*' ['typed_arg'] (',' 'default_arg')* [',' ['kwargs']]
 */
public final class ArgsKwargs extends NodeWrapper {

    public ArgsKwargs(ParseTreeNode node) {
        super(ParserRules.ARGS_KWARGS, node);
    }

    public TypedArg typedArg() {
        return get(1, TypedArg::new);
    }

    public boolean hasTypedArg() {
        return has(1);
    }

    public List<ArgsKwargs3> defaultArgs() {
        return getList(2, ArgsKwargs3::new);
    }

    public ArgsKwargs4 argsKwargs4() {
        return get(3, ArgsKwargs4::new);
    }

    public boolean hasArgsKwargs4() {
        return has(3);
    }

    /**
     * ',' 'default_arg'
     */
    public static final class ArgsKwargs3 extends NodeWrapper {

        public ArgsKwargs3(ParseTreeNode node) {
            super(ParserRules.ARGS_KWARGS_3, node);
        }

        public DefaultArg defaultArg() {
            return get(1, DefaultArg::new);
        }
    }

    /**
     * ',' ['kwargs']
     */
    public static final class ArgsKwargs4 extends NodeWrapper {

        public ArgsKwargs4(ParseTreeNode node) {
            super(ParserRules.ARGS_KWARGS_4, node);
        }

        public Kwargs kwargs() {
            return get(1, Kwargs::new);
        }

        public boolean hasKwargs() {
            return has(1);
        }
    }
}
