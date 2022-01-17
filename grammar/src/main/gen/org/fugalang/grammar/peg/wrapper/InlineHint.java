package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * inline_hint:
 * *   | '>>' [return_type]
 */
public final class InlineHint extends NodeWrapper {

    public InlineHint(ParseTreeNode node) {
        super(node);
    }

    public ReturnType returnType() {
        return new ReturnType(get(1));
    }

    public boolean hasReturnType() {
        return has(1);
    }
}
