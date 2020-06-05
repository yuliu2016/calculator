package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * optional: '[' or_rule ']'
 */
public final class Optional extends NodeWrapper {

    public Optional(ParseTreeNode node) {
        super(node);
    }

    public OrRule orRule() {
        return new OrRule(get(1));
    }
}
