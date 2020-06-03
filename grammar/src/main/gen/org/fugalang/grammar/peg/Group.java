package org.fugalang.grammar.peg;

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
        return new OrRule(get(1));
    }
}
