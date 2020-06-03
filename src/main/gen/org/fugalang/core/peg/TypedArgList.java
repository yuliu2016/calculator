package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * typed_arg_list: kwargs | args_kwargs | full_arg_list
 */
public final class TypedArgList extends NodeWrapper {

    public TypedArgList(ParseTreeNode node) {
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

    public FullArgList fullArgList() {
        return new FullArgList(get(2));
    }

    public boolean hasFullArgList() {
        return has(2);
    }
}
