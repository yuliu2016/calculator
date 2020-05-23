package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * typed_arg_list: 'kwargs' | 'args_kwargs' | 'full_arg_list'
 */
public final class TypedArgList extends NodeWrapper {

    public TypedArgList(ParseTreeNode node) {
        super(ParserRules.TYPED_ARG_LIST, node);
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

    public FullArgList fullArgList() {
        return get(2, FullArgList::new);
    }

    public boolean hasFullArgList() {
        return has(2);
    }
}
