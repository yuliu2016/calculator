package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * group:
 * *   | '(' alt_list ')'
 */
public final class Group extends NodeWrapper {

    public Group(ParseTreeNode node) {
        super(node);
    }

    public AltList altList() {
        return new AltList(get(1));
    }
}
