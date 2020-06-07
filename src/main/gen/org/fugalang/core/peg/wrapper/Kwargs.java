package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * kwargs:
 * *   | '**' typed_arg [',']
 */
public final class Kwargs extends NodeWrapper {

    public Kwargs(ParseTreeNode node) {
        super(node);
    }

    public TypedArg typedArg() {
        return new TypedArg(get(1));
    }

    public boolean isComma() {
        return is(2);
    }
}
