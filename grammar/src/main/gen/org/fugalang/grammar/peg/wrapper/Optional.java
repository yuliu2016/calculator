package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * optional:
 * *   | '[' alt_list ']'
 */
public final class Optional extends NodeWrapper {

    public Optional(ParseTreeNode node) {
        super(node);
    }

    public AltList altList() {
        return new AltList(get(1));
    }
}
