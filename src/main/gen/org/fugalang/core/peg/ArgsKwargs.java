package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * args_kwargs: '*' [typed_arg] (',' default_arg)* [',' [kwargs]]
 */
public final class ArgsKwargs extends NodeWrapper {

    public ArgsKwargs(ParseTreeNode node) {
        super(node);
    }

    public TypedArg typedArg() {
        return new TypedArg(get(1));
    }

    public boolean hasTypedArg() {
        return has(1);
    }

    public List<ArgsKwargs3> commaDefaultArgs() {
        return getList(2, ArgsKwargs3::new);
    }

    public ArgsKwargs4 argsKwargs4() {
        return new ArgsKwargs4(get(3));
    }

    public boolean hasArgsKwargs4() {
        return has(3);
    }

    /**
     * ',' default_arg
     */
    public static final class ArgsKwargs3 extends NodeWrapper {

        public ArgsKwargs3(ParseTreeNode node) {
            super(node);
        }

        public DefaultArg defaultArg() {
            return new DefaultArg(get(1));
        }
    }

    /**
     * ',' [kwargs]
     */
    public static final class ArgsKwargs4 extends NodeWrapper {

        public ArgsKwargs4(ParseTreeNode node) {
            super(node);
        }

        public Kwargs kwargs() {
            return new Kwargs(get(1));
        }

        public boolean hasKwargs() {
            return has(1);
        }
    }
}
