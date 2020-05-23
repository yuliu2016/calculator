package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.FugaRules;

/**
 * kwargs: '**' 'typed_arg' [',']
 */
public final class Kwargs extends NodeWrapper {

    public Kwargs(ParseTreeNode node) {
        super(FugaRules.KWARGS, node);
    }

    public TypedArg typedArg() {
        return get(1, TypedArg.class);
    }

    public boolean isComma() {
        return is(2);
    }
}
