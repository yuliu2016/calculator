package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * kwargs: '**' 'typed_arg' [',']
 */
public final class Kwargs extends NodeWrapper {

    public Kwargs(ParseTreeNode node) {
        super(ParserRules.KWARGS, node);
    }

    public TypedArg typedArg() {
        return get(1, TypedArg::new);
    }

    public boolean isComma() {
        return is(2);
    }
}
