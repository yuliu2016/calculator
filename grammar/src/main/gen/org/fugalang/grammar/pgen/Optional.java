package org.fugalang.grammar.pgen;

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
        return get(1, OrRule.class);
    }
}
