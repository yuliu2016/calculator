package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * group: '(' or_rule ')'
 */
public final class Group extends NodeWrapper {

    public Group(ParseTreeNode node) {
        super(node);
    }

    public OrRule orRule() {
        return get(1, OrRule.class);
    }
}
